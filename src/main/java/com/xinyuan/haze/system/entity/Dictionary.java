package com.xinyuan.haze.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinyuan.haze.core.jpa.entity.SimpleBaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统字典实体类
 * 
 * @author Sofar
 */
@Entity
@Table(name="SYS_DICTIONARY",uniqueConstraints={@UniqueConstraint(columnNames={"PARENT_ID","CODE"})})
@JsonIgnoreProperties(value = {"childs"})
public class Dictionary extends SimpleBaseEntity<String> {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 字典名称
	 */
	private String name;
	
	/**
	 * 字典代码
	 */
	private String code;
	
	/**
	 * 所属字典分类
	 */
	private Dictionary parent;
	
	/**
	 * 所有字典子集合
	 */
	private Set<Dictionary> childs = new HashSet<Dictionary>();

	/**
	 * 是否启用
	 */
	private Boolean isEnabled;
	
	/**
	 * 字典说明
	 */
	private String description;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@ManyToOne
	@JoinColumn(name="parent_id")
	public Dictionary getParent() {
		return parent;
	}

	public void setParent(Dictionary parent) {
		this.parent = parent;
	}

	@OneToMany(mappedBy="parent", orphanRemoval=true)
	public Set<Dictionary> getChilds() {
		return childs;
	}

	public void setChilds(Set<Dictionary> childs) {
		this.childs = childs;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Dictionary [name=" + name + ", code=" + code + ", parent="
				+ parent + ", isEnabled=" + isEnabled + ", description="
				+ description + "]";
	}

	
	private String pid;

	@Transient
	public String getPid() {
		return parent != null ? parent.getId() : null;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	private Integer sn = 0;

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

}