package by.bsu.dependency.examples.HardCodedSingletonApplicationContextExample;

import by.bsu.dependency.context.ApplicationContext;
import by.bsu.dependency.context.HardCodedSingletonApplicationContext;


public class HardCodedSingletonApplicationContextExample {
    public static void main(String[] args) {

        //HardCodedSingletonApplicationContext example
        try {
        ApplicationContext applicationContextHardcode = new HardCodedSingletonApplicationContext(
                HardFirstBean.class, HardSecondBean.class
        );
        applicationContextHardcode.start();

        HardFirstBean firstBean = (HardFirstBean) applicationContextHardcode.getBean("firstBean");
        HardSecondBean secondBean = (HardSecondBean) applicationContextHardcode.getBean("secondBean");

        firstBean.doSomething();
        secondBean.doSomething();
        try {
            secondBean.doSomethingWithFirst();
        } catch (RuntimeException e) {
            System.out.print("Caught exception: ");
            System.out.println(e.getMessage());
        }
        } catch (RuntimeException e) {
            System.out.print("Caught exception: ");
            System.out.println(e.getMessage());
        }
        // Метод падает, так как в классе HardCodedSingletonApplicationContext не реализовано внедрение зависимостей
    }
}
