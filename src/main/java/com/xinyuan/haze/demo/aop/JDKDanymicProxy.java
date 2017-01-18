package com.xinyuan.haze.demo.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Sofar on 2017/1/5.
 */
public class JDKDanymicProxy implements InvocationHandler {

    private Object target;

    public JDKDanymicProxy(Object target) {
        this.target = target;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target, args);
        after();
        return result;
    }

    public void before() {
        System.out.println("Before");
    }

    public void after() {
        System.out.println("After");
    }

    public static void main(String[] args) {
        Greeting greeting = new JDKDanymicProxy(new GreetingImpl()).getProxy();
        greeting.sayHello();
    }
}
