package com.xinyuan.haze.demo.aop;

/**
 * Created by Sofar on 2017/1/5.
 */
public class GreetingImplProxy implements  Greeting {

    private GreetingImpl greeting;

    public GreetingImplProxy(GreetingImpl greeting) {
        this.greeting = greeting;
    }

    @Override
    public void sayHello() {
        greeting.sayHello();
    }

    public void before() {
        System.out.println("before......");
    }

    public void after() {
        System.out.println("after......");
    }
}
