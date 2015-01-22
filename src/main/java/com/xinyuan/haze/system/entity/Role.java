package com.xinyuan.haze.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinyuan.haze.common.utils.HazeStringUtils;
import com.xinyuan.haze.core.jpa.entity.SimpleBaseEntity;
import com.xinyuan.haze.system.utils.Status;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统角色实体类
 *
 * @author sofar
 *
 */
@Entity
@Table(name="SYS_ROLE")
@JsonIgnoreProperties(value={"users","resources","groups"})
public class Role extends SimpleBaseEntity<String> {
	
	private static final long serialVersionUID = 1L;

    /**
     * 角色代码名称
     */
	private String code;

    /**
     * 角色名称
     */
	private String name;
	
	/**
	 * 角色状态
	 */
	private Status status;
	
	/**
	 * 角色资源
	 */
	private Set<Resource> resources = new HashSet<>();

	/**
	 * 角色用户
	 */
	private Set<User> users = new HashSet<>();

	/**
	 * 角色机构
	 */
	private Set<Group> groups = new HashSet<>();
	
	public Role() {
	}
	
    @Column(unique=true)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated(EnumType.STRING)
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@ManyToMany
	@JoinTable(
			name="SYS_ROLE_RESOURCE"
			, joinColumns={
				@JoinColumn(name="ROLE_ID")
				}
			, inverseJoinColumns={
				@JoinColumn(name="RESOURCE_ID")
				}
			)
	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	@ManyToMany(mappedBy="roles")
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@ManyToMany(mappedBy="roles")
	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	/**
	 * 对角色增加资源权限
	 * @param resource 资源对象
	 * @return 当前角色所有资源权限
	 */
	public Set<Resource> addResource(Resource resource) {
		this.resources.add(resource);
		return this.resources;
	}
	
	/**
	 * 删除角色资源权限
	 * @param resource 资源对象
	 * @return 当前角色所有资源权限
	 */
	public Set<Resource> removeResource(Resource resource) {
		this.resources.remove(resource);
		return this.resources;
	}

	@Transient
	public Set<String> getAllPermissons() {
		Set<String> permissions = new HashSet<String>();
		Set<Resource> resources = this.getResources();
		for (Resource resource : resources) {
			if (HazeStringUtils.isNotEmpty(resource.getPermission())) {
				permissions.add(resource.getPermission());
			}
		}
		return permissions;
	}

	@Override
	public String toString() {
		return "Role [name=" + name + ", code=" + code + ", status=" + status.getStatusName()
				+ "]";
	}
	
}