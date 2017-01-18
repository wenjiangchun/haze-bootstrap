package com.xinyuan.haze.demo.aop;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLibDynamicProxy implements MethodInterceptor {

    static CGLibDynamicProxy proxy = new CGLibDynamicProxy();
    private CGLibDynamicProxy() {
    }

    public static  CGLibDynamicProxy getInstance() {
        return proxy;
    }
    public <T> T getProxy(Class<T> tClass) {
        return (T)Enhancer.create(tClass, this);
    }
     @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
         before();
         Object result = methodProxy.invokeSuper(o, objects);
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
        CGLibDynamicProxy.getInstance().getProxy(GreetingImpl.class).sayHello();
    }
}
