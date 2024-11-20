package by.bsu.dependency.examples.HardCodedSingletonApplicationContextExample;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.Inject;
import by.bsu.dependency.annotation.PostConstruct;

@Bean(name = "secondBean")
public class HardSecondBean {

    @Inject
    private HardFirstBean firstBean;

    void doSomething() {
        System.out.println("Hi, I'm other bean\n");
    }

    void doSomethingWithFirst() {
        System.out.println("Trying to shake first bean...\n");
        firstBean.doSomething();
    }
}