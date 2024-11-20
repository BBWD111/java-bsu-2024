package by.bsu.dependency.context;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.BeanScope;
import exceptions.NoSuchBeanDefinitionException;

public class SimpleApplicationContext extends AbstractApplicationContext {

    public SimpleApplicationContext(Class<?>... beanClasses) {
        for (Class<?> beanClass : beanClasses) {
            String beanName = getBeanName(beanClass);
            beanDefinitions.put(beanName, beanClass);
            Bean beanAnnotation = beanClass.getAnnotation(Bean.class);
            if (beanAnnotation == null || beanAnnotation.scope() == BeanScope.SINGLETON) {
                singletonBeans.put(beanName, null);
            }
        }
    }
}