package by.bsu.dependency.examplesForTests;

import by.bsu.dependency.context.ApplicationContext;
import by.bsu.dependency.context.HardCodedSingletonApplicationContext;
import by.bsu.dependency.context.SimpleApplicationContext;
import exceptions.ApplicationContextNotStartedException;
import exceptions.NoSuchBeanDefinitionException;

public class Main {

    public static void main(String[] args) {

        //HardCodedSingletonApplicationContext example
            ApplicationContext applicationContextHardcode = new HardCodedSingletonApplicationContext(
                    FirstBean.class, OtherBean.class
            );
        /*ApplicationContext applicationContext2 = new SimpleApplicationContext(
                FirstBean.class, OtherBean.class
        );*/
            applicationContextHardcode.start();

            FirstBean firstBean = (FirstBean) applicationContextHardcode.getBean("firstBean");
            OtherBean otherBean = (OtherBean) applicationContextHardcode.getBean("otherBean");

            firstBean.doSomething();
            otherBean.doSomething();
            try {
                otherBean.doSomethingWithFirst();
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        // Метод падает, так как в классе HardCodedSingletonApplicationContext не реализовано внедрение зависимостей
        // otherBean.doSomethingWithFirst();
    }
}
