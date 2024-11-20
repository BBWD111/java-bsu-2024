package by.bsu.dependency.examplesForTests;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.Inject;
import by.bsu.dependency.annotation.BeanScope;
import by.bsu.dependency.annotation.PostConstruct;

@Bean(name = "thirdBean", scope = BeanScope.PROTOTYPE)
public class ThirdBeanSame {

    @Inject
    private FirstBean firstBean;

    @Inject
    private NotBean notBean;

    public void printSomething() {
        System.out.println("Hello, I'm fake Third bean, I'm the same as third bean and I'm a prototype");
    }

    public void doSomethingWithFirst() {
        firstBean.printSomething();
        notBean.printSomething();
    }

    @PostConstruct
    public void init() {
        System.out.println("Post construct method is initialized");
    }
}