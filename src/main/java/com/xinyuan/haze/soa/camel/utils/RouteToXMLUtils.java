package com.xinyuan.haze.soa.camel.utils;

import com.xinyuan.haze.soa.camel.entity.MyRoute;

import java.util.List;

/**
 * 将持久化路由信息转换成供CamelContext装载用的XML格式信息。
 *
 * Created by sofar on 15-10-23.
 */
public final class RouteToXMLUtils {

    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    private static final String XML_ROOT_ELEMENT = "routes";

    private static final String XML_ROUTE_ELEMENT = "route";

    /**
     * 加载camel XML配置路由命名空间
     */
    private static final String XML_ROOT_NAMESPACE = "http://camel.apache.org/schema/spring";

    /**
     * 获取xml格式的路由信息，以便于CamelContext加载.
     * <pre>
     *        <routes xmlns="http://camel.apache.org/schema/spring">
     *          <route id="userService" group="ws">
     *              <from uri="servlet:///test"/>
     *              <to uri="http://localhost:8080/haze/cxf/userService?bridgeEndpoint=true"/>
     *              <removeHeaders pattern="CamelHttp*"/>
     *              <to uri="log:output"/></route>
     *         </routes>
     * </pre>
     * @return xml格式的路由集合信息。
     */
   public static String getXMLRoutes(List<MyRoute> myRouteList) {
       StringBuilder sb = new StringBuilder();
       sb.append(XML_HEADER);
       sb.append("<routes xmlns=" + XML_ROOT_NAMESPACE + ">");

       myRouteList.forEach(myRoute -> {
           sb.append("<route id=" + myRoute.getKey() + ">");
           sb.append(myRoute.getContent());
           sb.append("</route>");
       });
       sb.append("</routes>");
       return sb.toString();
   }
}
