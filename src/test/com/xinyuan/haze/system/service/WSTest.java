package com.xinyuan.haze.system.service;

import com.xinyuan.haze.soa.camel.entity.MyRoute;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.dom4j.DocumentException;
import org.junit.Test;

/**
 * Created by sofar on 15-10-19.
 */

public class WSTest {
   /* public static void main(String[] args) {
      *//*  String wsdlUrl = "http://localhost:8080/haze/camel/test?wsdl";
        JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
        Client client = factory.createClient(wsdlUrl);
        Object[] params = {"1",1};
        try {
            Object[] res = client.invoke("getUser",params);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }*//*


    }
*/
    @Test
    public void test() {
        MyRoute myRoute = new MyRoute();
        myRoute.setContent("<route id=\"userService\" group=\"ws\">\n" +
                "        <from uri=\"servlet:///test\"/>\n" +
                "        <to uri=\"http://localhost:8080/haze/cxf/userService?bridgeEndpoint=true\"/>\n" +
                "        <removeHeaders pattern=\"CamelHttp*\"/>\n" +
                "        <to uri=\"log:output\"/></route>");
        try {
            System.out.println(myRoute.getXMLRoute());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
