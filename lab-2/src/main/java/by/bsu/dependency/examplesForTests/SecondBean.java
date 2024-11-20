package by.bsu.dependency.examplesForTests;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.Inject;
import by.bsu.dependency.annotation.PostConstruct;

@Bean(name = "secondBean")
public class SecondBean {

    @Inject
    private FirstBean firstBean;

    public void doSomething() {
        System.out.println("Hi, I'm second bean");
    }

    public void doSomethingWithFirst() {
        System.out.println("Trying to shake first bean...");
        firstBean.doSomething();
    }

    @PostConstruct
    public void init() {
        System.out.println("Post construct method for is initialized");
    }
}