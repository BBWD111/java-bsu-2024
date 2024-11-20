package by.bsu.dependency.context;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.BeanScope;
import by.bsu.dependency.annotation.Inject;
import by.bsu.dependency.annotation.PostConstruct;
import exceptions.ApplicationContextNotStartedException;
import exceptions.CyclicDependencyException;
import exceptions.NoSuchBeanDefinitionException;

public abstract class AbstractApplicationContext implements ApplicationContext {

    protected Map<String, Class<?>> beanDefinitions = new HashMap<>();
    protected final Map<String, Object> singletonBeans = new HashMap<>();
    protected final Stack<String> instantiationStack = new Stack<>();
    ContextStatus contextStatus = ContextStatus.NOT_STARTED;

    protected enum ContextStatus {
        NOT_STARTED,
        STARTED
    }

    public String getBeanName(Class<?> beanClass) {
        if (beanClass.isAnnotationPresent(Bean.class)) {
            return beanClass.getAnnotation(Bean.class).name();
        }
        String className = beanClass.getSimpleName();
        return Character.toLowerCase(className.charAt(0)) + className.substring(1);
    }

    @Override
    public void start() {
        if (isRunning()) {
            return;
        }
        contextStatus = ContextStatus.STARTED;
        try {
            for (Map.Entry<String, Class<?>> entry : beanDefinitions.entrySet()) {
                String beanName = entry.getKey();
                Class<?> beanClass = entry.getValue();
                if (singletonBeans.containsKey(beanName)) {
                    singletonBeans.put(beanName, instantiateBean(beanClass));
                }
            }
            for (Map.Entry<String, Object> entry : singletonBeans.entrySet()) {
                invokePostConstruct(entry.getValue());
            }
        } catch (RuntimeException e) {
            contextStatus = ContextStatus.NOT_STARTED;
            throw e;
        }
    }

    @Override
    public boolean isRunning() {
        return contextStatus == ContextStatus.STARTED;
    }

    @Override
    public boolean containsBean(String name) {
        if (!isRunning()) {
            throw new ApplicationContextNotStartedException();
        }
        return beanDefinitions.containsKey(name);
    }

    @Override
    public Object getBean(String name) {
        if (!isRunning()) {
            throw new ApplicationContextNotStartedException();
        }
        if (!beanDefinitions.containsKey(name)) {
            throw new NoSuchBeanDefinitionException();
        }
        Class<?> beanClass = beanDefinitions.get(name);
        if (singletonBeans.containsKey(name)) {
            if (singletonBeans.get(name) == null) {
                singletonBeans.put(name, instantiateBean(beanClass));
            }
            return singletonBeans.get(name);
        }
        Object prototypeBean = instantiateBean(beanClass);
        invokePostConstruct(prototypeBean);
        return prototypeBean;
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        for (Map.Entry<String, Class<?>> entry : beanDefinitions.entrySet()) {
            if (entry.getValue().equals(clazz)) {
                return clazz.cast(getBean(entry.getKey()));
            }
        }
        throw new NoSuchBeanDefinitionException();
    }

    @Override
    public boolean isPrototype(String name) {
        if (!beanDefinitions.containsKey(name)) {
            throw new NoSuchBeanDefinitionException();
        }
        return !singletonBeans.containsKey(name);
    }

    @Override
    public boolean isSingleton(String name) {
        if (!beanDefinitions.containsKey(name)) {
            throw new NoSuchBeanDefinitionException();
        }
        return singletonBeans.containsKey(name);
    }

    protected <T> T instantiateBean(Class<T> beanClass) {
        String beanName = getBeanName(beanClass);
        if (instantiationStack.contains(beanName)) {
            throw new CyclicDependencyException();
        }
        instantiationStack.push(beanName);
        try {
            Constructor<T> constructor = beanClass.getConstructor();
            constructor.setAccessible(true);
            T bean = constructor.newInstance();
            injectDependencies(bean);
            return bean;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException e) {
            throw new RuntimeException(e);
        } finally {
            instantiationStack.pop();
        }
    }

    protected void injectDependencies(Object bean) {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                String fieldName = getBeanName(field.getType());
                Object dependency = getBean(fieldName);
                field.setAccessible(true);
                try {
                    field.set(bean, dependency);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to inject dependency", e);
                }
            }
        }
    }

    protected void invokePostConstruct(Object bean) {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                try {
                    method.setAccessible(true);
                    method.invoke(bean);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("Failed to invoke Post Construct method");
                }
            }
        }
    }
}