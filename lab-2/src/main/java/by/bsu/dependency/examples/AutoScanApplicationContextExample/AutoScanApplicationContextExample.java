package by.bsu.dependency.examples.AutoScanApplicationContextExample;

import by.bsu.dependency.context.ApplicationContext;
import by.bsu.dependency.context.AutoScanApplicationContext;
import exceptions.ApplicationContextNotStartedException;
import exceptions.NoSuchBeanDefinitionException;

public class AutoScanApplicationContextExample {
    public static void main(String[] args) {
        try {
            ApplicationContext applicationContext = new AutoScanApplicationContext("by.bsu.dependency.examples.AutoScanApplicationContextExample");
            applicationContext.start();

            AutoFirstBean firstBean = (AutoFirstBean) applicationContext.getBean("firstBean");
            AutoSecondBean secondBean = (AutoSecondBean) applicationContext.getBean("secondBean");
            //AutoNotBean notBean = (AutoNotBean) applicationContext.getBean("autoNotBean"); throws NoSuchBeanDefinitionException

            firstBean.printSomething();
            firstBean.doSomething();
            secondBean.doSomething();
            secondBean.doSomethingWithFirst();

        } catch (ApplicationContextNotStartedException | NoSuchBeanDefinitionException e) {
            System.out.println(e.getMessage());
        }
    }
}
