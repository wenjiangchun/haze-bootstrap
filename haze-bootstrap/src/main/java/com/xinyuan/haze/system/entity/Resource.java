package com.xinyuan.haze.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinyuan.haze.core.jpa.entity.SimpleBaseEntity;
import com.xinyuan.haze.system.utils.ResourceType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统资源权限实体类
 * 
 * @author Sofar
 *
 */
@Entity
@Table(name="SYS_RESOURCE")
@JsonIgnoreProperties(value={"childrens","roles"})
public class Resource extends SimpleBaseEntity<String> { 
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String permission;

	private String url;
	
	private ResourceType resourceType;
	
	/**
	 * 上级资源
	 */
	private Resource parent;
	
	/**
	 * 子机构
	 */
	private Set<Resource> childrens = new HashSet<>();
	
	/**
	 * 资源角色
	 */
	private Set<Role> roles = new HashSet<>();

	public Resource() {
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	@Column(unique=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Enumerated(EnumType.STRING)
	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	@ManyToOne
	@JoinColumn(name="parent_id")
	public Resource getParent() {
		return parent;
	}

	public void setParent(Resource parent) {
		this.parent = parent;
	}

	@OneToMany(mappedBy="parent")
	public Set<Resource> getChildrens() {
		return childrens;
	}

	public void setChildrens(Set<Resource> childrens) {
		this.childrens = childrens;
	}

	@ManyToMany(mappedBy="resources")
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void removeAllChildrens() {
		for (Resource resource : this.childrens) {
			resource.setParent(null);
		}
	}

	@Override
	public String toString() {
		String url = this.url != null ? this.url : "";
		return "Resource [permission=" + permission + ",name=" + name + ",resourceType=" + resourceType.getTypeName() + ",url=" + url + "]";
	}
}