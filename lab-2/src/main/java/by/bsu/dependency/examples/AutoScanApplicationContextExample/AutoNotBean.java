package by.bsu.dependency.examples.AutoScanApplicationContextExample;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.BeanScope;
import by.bsu.dependency.annotation.PostConstruct;

public class AutoNotBean {
    void printSomething() {
        System.out.println("Hello, I'm NOT a bean");
    }

    void doSomething() {
        System.out.println("NOT a bean is working on a project...");
    }
}
