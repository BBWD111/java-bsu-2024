package by.bsu.dependency.examples.AutoScanApplicationContextExample;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.Inject;
import by.bsu.dependency.annotation.PostConstruct;

@Bean(name = "secondBean")
public class AutoSecondBean {
    @Inject
    private AutoFirstBean firstBean;

    void doSomething() {
        System.out.println("Hi, I'm other bean\n");
    }

    void doSomethingWithFirst() {
        System.out.println("Trying to shake first bean...\n");
        firstBean.doSomething();
    }

    @PostConstruct
    public void init() {
        System.out.println("Post construct method for secondBean is initialized");
    }
}
