package by.bsu.dependency.examples.HardCodedSingletonApplicationContextExample;

import by.bsu.dependency.annotation.Bean;

@Bean(name = "firstBean")
public class HardFirstBean {

    void printSomething() {
        System.out.println("Hello, I'm first bean\n");
    }

    void doSomething() {
        System.out.println("First bean is working on a project...\n");
    }
}
