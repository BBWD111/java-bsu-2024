package by.bsu.dependency.context;

import org.reflections.Reflections;
import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.BeanScope;

import java.util.Set;

public class AutoScanApplicationContext extends AbstractApplicationContext {

    public AutoScanApplicationContext(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> beanClasses = reflections.getTypesAnnotatedWith(Bean.class);
        for (Class<?> beanClass : beanClasses) {
            String beanName = getBeanName(beanClass);
            beanDefinitions.put(beanName, beanClass);
            Bean beanAnnotation = beanClass.getAnnotation(Bean.class);
            if (beanAnnotation != null && beanAnnotation.scope() == BeanScope.SINGLETON) {
                singletonBeans.put(beanName, null);
            }
        }
    }
}