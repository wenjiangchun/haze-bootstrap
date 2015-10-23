package com.xinyuan.haze.soa.camel.entity;

import com.xinyuan.haze.core.jpa.entity.SimpleBaseEntity;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 路由实体类
 * <p>
 *     该实体对应{@link org.apache.camel.Route}中信息，{@link org.apache.camel.CamelContext}中路由启动修改停止具体信息存放在该实体对应数据库中，当系统启动后加载
 * 该库中的所有路由信息至{@link org.apache.camel.CamelContext}中。
 * </p>
 * @see org.apache.camel.CamelContext
 * @see com.xinyuan.haze.soa.camel.spring.SpringCamelContextUtils
 * Created by sofar on 15-10-20.
 */
@Entity
@Table(name="CAMEL_MY_ROUTE")
public class MyRoute extends SimpleBaseEntity<String> {

    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    private static final String XML_ROOT_ELEMENT = "routes";

    private static final String XML_ROUTE_ELEMENT = "route";

    private static final String XML_ROOT_NAMESPACE = "http://camel.apache.org/schema/spring";

    @Column(unique=true)
    private String key;

    /**
     * 路由内容，为xml格式
     * <p>
     *     eg:
     *     <routes xmlns="http://camel.apache.org/schema/spring">
              <route id="userService" group="ws">
              <from uri="servlet:///test"/>
              <to uri="http://localhost:8080/haze/cxf/userService?bridgeEndpoint=true"/>
              <removeHeaders pattern="CamelHttp*"/>
              <to uri="log:output"/></route>
           </routes>
     * </p>
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 路由状态
     */
    private String status;

    /**
     * 路由说明
     */
    private String description;

    /**
     * 路由类型
     */
    private String type;

    /**
     * 路由分组
     */
    private String group1;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getGroup1() {
        return group1;
    }

    public void setGroup1(String group1) {
        this.group1 = group1;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return null;
    }

    /**
     * 获取xml格式的路由信息，以便于CamelContext加载.
     *        <routes xmlns="http://camel.apache.org/schema/spring">
                 <route id="userService" group="ws">
                 <from uri="servlet:///test"/>
                 <to uri="http://localhost:8080/haze/cxf/userService?bridgeEndpoint=true"/>
                 <removeHeaders pattern="CamelHttp*"/>
                 <to uri="log:output"/></route>
              </routes>
     * @return
     */
    @Transient
    public String getXMLRoute() throws DocumentException {
        /*Document document = DocumentHelper.createDocument();
        document.setXMLEncoding("UTF-8");
        Element root = document.addElement(XML_ROOT_ELEMENT, XML_ROOT_NAMESPACE);
        //root.addText(this.getContent());
        Document d = DocumentHelper.parseText(this.getContent());
        Element route = d.getRootElement();
        route.remove("xmlns");
        route.re
        root.add(route);
        return document.asXML();*/
        return null;
    }
}
