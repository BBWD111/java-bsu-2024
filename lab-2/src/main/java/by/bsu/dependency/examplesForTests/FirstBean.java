package by.bsu.dependency.examplesForTests;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.PostConstruct;

@Bean(name = "firstBean")
public class FirstBean {

    void printSomething() {
        System.out.println("Hello, I'm first bean\n");
    }

    void doSomething() {
        System.out.println("First bean is working on a project...\n");
    }

    @PostConstruct
    public void init() {
        System.out.println("Post construct method is initialized");
    }
}
