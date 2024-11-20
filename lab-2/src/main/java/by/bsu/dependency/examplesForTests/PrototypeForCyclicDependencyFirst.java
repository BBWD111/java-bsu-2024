package by.bsu.dependency.examplesForTests;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.Inject;
import by.bsu.dependency.annotation.BeanScope;
import by.bsu.dependency.annotation.PostConstruct;

@Bean(name = "prototypeBean1", scope = BeanScope.PROTOTYPE)
public class PrototypeForCyclicDependencyFirst {

    @Inject
    private PrototypeForCyclicDependencySecond prototype2;

    public void doSomething() {
        System.out.println("PrototypeBean1 is doing something");
        prototype2.doSomething();
    }

    @PostConstruct
    public void init() {
        System.out.println("Post construct method for is initialized");
    }
}