package by.bsu.dependency.examplesForTests;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.Inject;

@Bean(name = "otherBean")
public class OtherBean {

    @Inject
    private FirstBean firstBean;

    void doSomething() {
        System.out.println("Hi, I'm other bean\n");
    }

    void doSomethingWithFirst() {
        System.out.println("Trying to shake first bean...\n");
        firstBean.doSomething();
    }

    /*@PostConstruct
    public void init() {
        System.out.println("Post construct method for is initialized");
    }*/
}
