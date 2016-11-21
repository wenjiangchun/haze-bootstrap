package com.xinyuan.haze.security.shiro;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinyuan.haze.system.entity.Resource;
import com.xinyuan.haze.system.entity.Role;
import com.xinyuan.haze.system.service.ResourceService;
import com.xinyuan.haze.system.service.RoleService;


/**
 * 借助spring {@link org.springframework.beans.factory.FactoryBean} 对apache shiro的premission进行动态创建
 * 
 * @author vincent
 *
 */
public class ChainDefinitionSectionMetaSource implements FactoryBean<Section>{

	//private static final String PERMS = "perms\\[(.*?)\\]";
	
	@Autowired
	private ResourceService resourceService;

	@Autowired
	private RoleService roleService;

	//shiro默认的链接定义
	private String filterChainDefinitions;
	
	public Section getObject() throws BeansException {
        
        List<Resource> resources = resourceService.findAll();
        List<Role> roles = roleService.findAll();
        Ini ini = new Ini();
        //加载默认的url
        ini.load(filterChainDefinitions);
        Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        //循环数据库资源的url
        for (Iterator<Resource> it = resources.iterator(); it.hasNext();) {
        	Resource resource = it.next();
        	if(StringUtils.isNotEmpty(resource.getUrl()) && StringUtils.isNotEmpty(resource.getPermission())) {
        		section.put(resource.getUrl(), "perms["+resource.getPermission()+"]");
        		//section.put(resource.getUrl(), resource.getPermission());
        		//section.put(resource.getUrl()+"/", resource.getPermission());
        	}
        }
        
        //循环数据库组的url
        for (Iterator<Role> it = roles.iterator(); it.hasNext();) {
        	
        	Role role = it.next();
        	if(StringUtils.isNotEmpty(role.getName()) && StringUtils.isNotEmpty(role.getName())) {
        		//section.put(role.getCname(), role.getName());
        	}
        	
        }
        //section.put("/**", "authc");  //对所有资源请求加上认证访问权限
		return section;
	}
	
	/**
	 * 通过filterChainDefinitions对默认的链接过滤定义
	 * 
	 * @param filterChainDefinitions 默认的接过滤定义
	 */
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	public void reload() {
		
	}
	public Class<?> getObjectType() {
		return this.getClass();
	}

	public boolean isSingleton() {
		return false;
	}
	
}
