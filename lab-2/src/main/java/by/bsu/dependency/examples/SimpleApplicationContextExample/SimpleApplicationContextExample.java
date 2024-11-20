package by.bsu.dependency.examples.SimpleApplicationContextExample;

import by.bsu.dependency.context.ApplicationContext;
import by.bsu.dependency.context.SimpleApplicationContext;
import exceptions.ApplicationContextNotStartedException;
import exceptions.CyclicDependencyException;
import exceptions.NoSuchBeanDefinitionException;

public class SimpleApplicationContextExample {
    public static void main(String[] args) {

        //SimpleApplicationContext example
        try {
            ApplicationContext applicationContextHardcode = new SimpleApplicationContext(
                    SimpleFirstBean.class, SimpleSecondBean.class
            );
            applicationContextHardcode.start();

            SimpleFirstBean firstBean = (SimpleFirstBean) applicationContextHardcode.getBean("firstBean");
            SimpleSecondBean secondBean = (SimpleSecondBean) applicationContextHardcode.getBean("secondBean");

            firstBean.init();
            secondBean.init();
            firstBean.doSomething();
            secondBean.doSomething();
            secondBean.doSomethingWithFirst();
        } catch (ApplicationContextNotStartedException | NoSuchBeanDefinitionException | CyclicDependencyException e) {
            System.out.println(e.getMessage());
        }
    }
}
