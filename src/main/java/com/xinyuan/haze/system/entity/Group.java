package com.xinyuan.haze.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xinyuan.haze.core.jpa.entity.SimpleBaseEntity;
import com.xinyuan.haze.system.utils.Status;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统组织机构实体类
 *
 * @author sofar
 */
@Entity
//@Table(name = "SYS_GROUP", uniqueConstraints = {@UniqueConstraint(columnNames = {"PARENT_ID", "CODE"})})
@Table(name = "SYS_GROUP")
@JsonIgnoreProperties(value = {"childs", "roles", "users"})
public class Group extends SimpleBaseEntity<String> {

    private static final long serialVersionUID = 1L;

    /**
     * 机构类型对应字典表中跟字典代码名称，当需要给机构添加机构类型时，首先在字典表中添加字典代码为"{@code GROUP_TYPE}"的
     * 跟字典对象，然后在该字典对象下添加需要的机构类型字典对象，当使用时则通过跟字典对象"{@code GROUP_TYPE}"去查找下面
     * 所有机构类型子字典对象。
     */
    public static final String GROUP_TYPE = "GROUP_TYPE";

    /**
     * 机构名称
     */
    private String name;

    /**
     * 机构全称
     */
    private String fullName;

    /**
     * 上级机构
     */
    private Group parent;

    /**
     * 子机构
     */
    private Set<Group> childs = new HashSet<>();

    /**
     * 机构下用户
     */
    private Set<User> users = new HashSet<>();



    /**
     * 机构角色组
     */
    private Set<Role> roles = new HashSet<>();

    /**
     * 机构代码
     */
    private String code;

    /**
     * 机构类型
     */
    private Dictionary groupType;
    /**
     * 机构地址
     */
    private String address;

    /**
     * 机构电话
     */
    private String tel;

    /**
     * 机构备注
     */
    private String remark;

    /**
     * 机构状态
     */
    private Status status = Status.ENABLE;

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @ManyToOne
    @JoinColumn(name = "parent_id")
    public Group getParent() {
        return parent;
    }

    public void setParent(Group parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent")
    public Set<Group> getChilds() {
        return childs;
    }

    public void setChilds(Set<Group> childs) {
        this.childs = childs;
    }

    @OneToMany(mappedBy = "group")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @ManyToMany
    @JoinTable(
            name = "SYS_GROUP_ROLE"
            , joinColumns = {
            @JoinColumn(name = "GROUP_ID")
    }
            , inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID")
    }
    )
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @ManyToOne
    @JoinColumn(name = "type_id")
    public Dictionary getGroupType() {
        return groupType;
    }

    public void setGroupType(Dictionary groupType) {
        this.groupType = groupType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 对组增加用户
     *
     * @param user 用户对象
     */
    public void addUser(User user) {
        user.setGroup(this);
    }

    /**
     * 对机构增加角色
     *
     * @param role 角色对象
     * @return 当前机构所有角色
     */
    public Set<Role> addRole(Role role) {
        this.roles.add(role);
        return this.roles;
    }

    /**
     * 删除机构角色
     *
     * @param role 角色对象
     * @return 当前机构所有角色
     */
    public Set<Role> removeRole(Role role) {
        this.roles.remove(role);
        return this.roles;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "Group [name=" + name + ", fullName=" + fullName + ", code="
                + code + ", address=" + address + ", tel=" + tel + "]";
    }

}
