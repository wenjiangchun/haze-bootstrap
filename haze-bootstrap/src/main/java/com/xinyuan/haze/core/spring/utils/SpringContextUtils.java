package com.xinyuan.haze.core.spring.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.util.Locale;

/**
 * Spring 上下文工具类，该类通过实现{@code ApplicationContextAware}接口获取Spring上下文
 * <p><b>说明：</b>在web应用程序中，实际获取到的ApplicationContext为XmlWebApplicationContext对象。</p>
 *
 * @author sofar
 * @see org.springframework.context.ApplicationContextAware
 * @see org.springframework.context.ApplicationContext
 * @see org.springframework.web.context.support.XmlWebApplicationContext
 */

public final class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        if (applicationContext.getParent() != null) {
            ctx = applicationContext.getParent();
        } else {
            ctx = applicationContext;
        }
    }

    /**
     * 根据beanName获取上下文Bean
     *
     * @param beanName Spring Bean's name
     * @return Object
     */
    public static Object getBean(String beanName) {
        return ctx.getBean(beanName);
    }

    /**
     * 根据Class类型获取Spring Bean
     *
     * @param requiredType Class
     * @return 上下文中符合参数类型的Class对象的一个Bean
     */
    public static <T> T getBean(Class<T> requiredType) {
        return ctx.getBean(requiredType);
    }

    /**
     * 根据beanName和Class类型获取Spring Bean
     *
     * @param beanName     beanName
     * @param requiredType Class
     * @return 上下文中符合参数类型的Class对象的一个Bean
     */
    public static <T> T getBean(String beanName, Class<T> requiredType) {
        return ctx.getBean(beanName, requiredType);
    }

    /**
     * 发布应用程序事件
     *
     * @param event 事件对象
     */
    public static void publishEvent(ApplicationEvent event) {
        ctx.publishEvent(event);
    }

    /**
     * 获取spring {@code ApplicationContext}对象。
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    /**
     * 获取国际化message信息
     *
     * @param message message-key
     * @return message-value
     */
    public static String getMessage(String message, String[] args, Locale locale) {
        return ctx.getMessage(message, args, locale);
    }

}
