package com.xinyuan.haze.soa.camel.spring;

import com.xinyuan.haze.soa.camel.entity.MyRoute;
import org.apache.camel.*;
import org.apache.camel.model.RoutesDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.InputStream;
import java.util.List;

/**
 * camel上下文工具类.
 * Created by sofar on 15-10-15.
 */
@Component
public class SpringCamelContextUtils implements CamelContextAware, InitializingBean {

    private static CamelContext camelContext;

    private Logger logger = LoggerFactory.getLogger(SpringCamelContextUtils.class);

    @Override
    public void setCamelContext(CamelContext camelContext) {
        this.camelContext = camelContext;
        //加载系统路由信息.
        logger.debug("加载系统路由信息");
    }

    @Override
    public CamelContext getCamelContext() {
        return camelContext;
    }

    public static void getRouts() {
       List<String> componentNames = camelContext.getComponentNames();
        List<Route> routes = camelContext.getRoutes();

        routes.forEach(name -> {
            System.out.println(name.getId());
            try {
                camelContext.stopRoute(name.getId());
                if (name.getRouteContext().getRoute().getStatus(camelContext) == ServiceStatus.Stopped) {
                    camelContext.removeRoute(name.getId());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //camelContext.addRouteDefinition(new RouteDefinition());
       /* RouteBuilder rb = new RouteBuilder() {
            @Override
            public void configure() throws Exception {

            }
        };
        camelContext.addRoutes();*/
    }

    public static void addRoute(MyRoute myRoute) throws Exception {
        InputStream is = ClassUtils.getDefaultClassLoader().getResourceAsStream("camel/config.xml");
        Class<?> hello = ClassUtils.getDefaultClassLoader().loadClass("com.xinyuan.haze.soa.camel.spring.HelloWorldImpl");

        HelloWord helloWord = (HelloWord) hello.newInstance();
        System.out.println(helloWord.hello("aaa"));
        //ClassUtils.getDefaultClassLoader().
        RoutesDefinition routesDefinition = camelContext.loadRoutesDefinition(is);
        routesDefinition.getRoutes().forEach(d -> {
            try {
                camelContext.addRouteDefinition(d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        List<Route> routes = camelContext.getRoutes();

        routes.forEach(name -> {
            System.out.println(name.getId());
            try {
                camelContext.stopRoute(name.getId());
                if (name.getRouteContext().getRoute().getStatus(camelContext) == ServiceStatus.Stopped) {
                    camelContext.removeRoute(name.getId());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

       /* //String xml = myRoute.getContent();
       *//* String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        xml += "<route><from>servlet:///test</from><to>http://localhost:8080/haze/cxf/userService?bridgeEndpoint=true</to></route>";*//*
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><routes><route><!-- incoming requests from the servlet is routed -->\n" +
                "<from uri=\"servlet:///hello\"/><choice><when><!-- is there a header with the key name? -->\n" +
                "<header>name</header><!-- yes so return back a message to the user -->\n" +
                "<to uri=\"bean:myBean\"/></when><otherwise><!-- if no name parameter then output a syntax to the user \n" +
                "--><transform><constant>Add a name parameter to uri, eg ?name=foo</constant></transform></otherwise></choice></route></routes>";
        *//*JAXBContext context = JAXBContext.newInstance(RouteDefinition.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        RouteDefinition routeDefinition = (RouteDefinition) unmarshaller.unmarshal(new StringReader(xml));
        System.out.println(routeDefinition);*//*
       *//* Document document = DocumentHelper.parseText(xml);
        Element element = document.getRootElement();
        if (!element.getName().equalsIgnoreCase("route")) {
            throw  new Exception("解析错误");
        }*//*
        InputStream is = ClassUtils.getDefaultClassLoader().getResourceAsStream("camel/config.xml");
        //ClassUtils.getDefaultClassLoader().
        camelContext.loadRoutesDefinition(is);*/
        /*camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                RouteDefinition definition = null;
                for (Object e : element.elements()) {
                    Element element1 = (Element) e;
                    if (element1.getName().equalsIgnoreCase("from")) {
                        definition = this.from(element1.getStringValue());
                    }
                    if (element1.getName().equalsIgnoreCase("to")) {
                       // this.sendTo(element1.getStringValue());
                        definition.to(element1.getStringValue());
                    }
                }
               *//* element.elements().forEach(e -> {
                    Element element1 = (Element) e;
                    if (element1.getName().equalsIgnoreCase("from")) {
                        definition = this.from(element1.getStringValue());
                    }
                    if (element1.getName().equalsIgnoreCase("to")) {
                        this.sendTo(element1.getStringValue());
                    }
                });*//*
            }
        });*/
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        //加载系统
        logger.debug(this.toString()+"初始化完毕");
    }
}
