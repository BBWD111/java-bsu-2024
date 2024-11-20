/*

package by.bsu.dependency.examplesForTests;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.Inject;
import by.bsu.dependency.annotation.PostConstruct;

@Bean(name = "singletonForCyclicDependency")
public class SingletonForCyclicDependency {
    @Inject
    private PrototypeForCyclicDependencyFirst prototype1;

    public void doSomething() {
        System.out.println("single is doing something");
        prototype1.doSomething();
    }

    @PostConstruct
    public void init() {
        System.out.println("Post construct method for is initialized");
    }
}

*/
