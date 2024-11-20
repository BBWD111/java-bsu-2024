package by.bsu.dependency.context;

import by.bsu.dependency.examplesForTests.*;
import exceptions.CyclicDependencyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import exceptions.NoSuchBeanDefinitionException;
import exceptions.ApplicationContextNotStartedException;

class SimpleApplicationContextTest {

    private ApplicationContext applicationContext;

    @BeforeEach
    void init() {
        applicationContext = new SimpleApplicationContext(
                FirstBean.class,
                SecondBean.class,
                ThirdBean.class,
                NotBean.class,
                PrototypeForCyclicDependencyFirst.class,
                PrototypeForCyclicDependencySecond.class
                //SingletonForCyclicDependency.class
        );
    }

    @Test
    void testIsRunning() {
        assertThat(applicationContext.isRunning()).isFalse();
        applicationContext.start();
        assertThat(applicationContext.isRunning()).isTrue();
    }

    @Test
    void testContextContainsNotStarted() {
        assertThrows(
                ApplicationContextNotStartedException.class,
                () -> applicationContext.containsBean("firstBean")
        );
    }

    @Test
    void testContextContainsBeans() {
        applicationContext.start();

        assertThat(applicationContext.containsBean("firstBean")).isTrue();
        assertThat(applicationContext.containsBean("secondBean")).isTrue();
        assertThat(applicationContext.containsBean("thirdBean")).isTrue();
        assertThat(applicationContext.containsBean("notBean")).isTrue();
        assertThat(applicationContext.containsBean("randomName")).isFalse();
    }

    @Test
    void testContextGetBeanNotStarted() {
        assertThrows(
                ApplicationContextNotStartedException.class,
                () -> applicationContext.getBean("firstBean")
        );
    }

    @Test
    void testGetBeanReturns() {
        applicationContext.start();

        assertThat(applicationContext.getBean("firstBean")).isNotNull().isInstanceOf(FirstBean.class);
        assertThat(applicationContext.getBean("secondBean")).isNotNull().isInstanceOf(SecondBean.class);
        assertThat(applicationContext.getBean("thirdBean")).isNotNull().isInstanceOf(ThirdBean.class);
        assertThat(applicationContext.getBean("notBean")).isNotNull().isInstanceOf(NotBean.class);
    }

    @Test
    void testGetBeanThrows() {
        applicationContext.start();

        assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> applicationContext.getBean("randomName")
        );
    }

    @Test
    void testIsSingletonReturns() {
        applicationContext.start();
        assertThat(applicationContext.isSingleton("firstBean")).isTrue();
        assertThat(applicationContext.isSingleton("secondBean")).isTrue();
        assertThat(applicationContext.isSingleton("thirdBean")).isFalse();
        assertThat(applicationContext.isSingleton("notBean")).isTrue();
    }

    @Test
    void testIsSingletonThrows() {
        assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> applicationContext.isSingleton("randomName")
        );
    }

    @Test
    void testIsPrototypeReturns() {
        applicationContext.start();
        assertThat(applicationContext.isPrototype("firstBean")).isFalse();
        assertThat(applicationContext.isPrototype("secondBean")).isFalse();
        assertThat(applicationContext.isPrototype("thirdBean")).isTrue();
        assertThat(applicationContext.isPrototype("notBean")).isFalse();
    }

    @Test
    void testIsPrototypeThrows() {
        assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> applicationContext.isPrototype("randomName")
        );
    }

    @Test
    void testInjections() {
        applicationContext.start();

        ThirdBean thirdBean = (ThirdBean) applicationContext.getBean("thirdBean");
        thirdBean.doSomethingWithFirst();
    }

    @Test
    void testPrototypeEquals() {
        applicationContext.start();

        ThirdBean thirdBean = (ThirdBean) applicationContext.getBean("thirdBean");
        ThirdBean thirdBean2 = (ThirdBean) applicationContext.getBean("thirdBean");

        assertThat(thirdBean).isNotSameAs(thirdBean2);
    }

    @Test
    void testSingletonEquality() {
        applicationContext.start();

        FirstBean firstBean = (FirstBean) applicationContext.getBean("firstBean");
        FirstBean firstBean2 = (FirstBean) applicationContext.getBean("firstBean");

        assertThat(firstBean).isSameAs(firstBean2);
    }

    @Test
    void testCyclicDependencyThrowsForPrototypes() {
        ApplicationContext applicationContext2 = new SimpleApplicationContext(
                PrototypeForCyclicDependencyFirst.class,
                PrototypeForCyclicDependencySecond.class
        );
        applicationContext2.start();
        assertThrows(
                CyclicDependencyException.class,
                () -> applicationContext2.getBean("prototypeBean1")
        );
    }
}