package by.bsu.dependency.examplesForTests;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.Inject;
import by.bsu.dependency.annotation.BeanScope;
import by.bsu.dependency.annotation.PostConstruct;

@Bean(name = "prototypeBean2", scope = BeanScope.PROTOTYPE)
public class PrototypeForCyclicDependencySecond {

    @Inject
    private PrototypeForCyclicDependencyFirst prototype1;

    public void doSomething() {
        System.out.println("PrototypeBean2 is doing something");
        prototype1.doSomething();
    }

    @PostConstruct
    public void init() {
        System.out.println("Post construct method for is initialized");
    }
}