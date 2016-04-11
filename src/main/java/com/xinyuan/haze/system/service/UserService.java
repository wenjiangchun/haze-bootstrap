package com.xinyuan.haze.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xinyuan.haze.common.utils.EncodeUtils;
import com.xinyuan.haze.common.utils.HazeStringUtils;
import com.xinyuan.haze.security.utils.Digests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.xinyuan.haze.core.jpa.service.AbstractBaseService;
import com.xinyuan.haze.system.dao.UserDao;
import com.xinyuan.haze.system.entity.Group;
import com.xinyuan.haze.system.entity.Role;
import com.xinyuan.haze.system.entity.User;
import com.xinyuan.haze.system.exception.UserLoginNameExistException;
import com.xinyuan.haze.system.utils.Status;

/**
 * 用户业务操作类
 * 
 * @author sofar
 *
 */
@Service
@Transactional(readOnly = true)
public class UserService extends AbstractBaseService<User, String> {

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;

	private UserDao userDao;
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
		super.setDao(userDao);
	}
	
	/**
	 * 根据用户登录名称查找用户对象
	 * @param loginName 登录名称
	 * @return 用户对象
	 */
	public User findByLoginName(String loginName) {
		logger.debug("find User by loginName={}", loginName);
		return this.userDao.findByLoginName(loginName);
	}
	
	/**
	 * 添加或更新用户对象，如果ID不存在则添加用户否则更新用户信息
	 * @param user 用户对象
	 * @return 保存或更新后的用户对象
	 * @throws Exception 如果登录名称已存在则抛出该异常
	 */
	@Transactional(readOnly=false)
	public User saveOrUpdate(User user) throws Exception {
		Assert.notNull(user);
		if (user.isNew()) {
			User u = this.userDao.findByLoginName(user.getLoginName());
			if (u != null) {
				logger.error("登陆名{}已存在，请重试！",user.getLoginName());
				throw new UserLoginNameExistException("登陆名" + user.getLoginName() + "已存在，请重试");
			} else {
				user.setPassword(User.DEFAULT_PASSWORD); //设置默认密码。
				entryptPassword(user); //加密用户密码
				logger.debug("保存用户，用户信息为：{}", user.toString());
			}
		} else {
			//更新用户对象
				logger.debug("更新用户，用户信息为：{}", user.toString());
			}
		user = this.userDao.save(user);
		return user;
	}
	
	@Override
	@Transactional(readOnly=false)
	public User save(User t) throws Exception {
		return saveOrUpdate(t);
	}

    /**
	 * 对用户赋角色权限，同时更新权限缓存中用户信息
	 * @param id 用户Id
	 * @param role 角色对象
     * @throws Exception 
	 */
	@CacheEvict(value="shiroCache",allEntries=true)
	@Transactional(readOnly = false)
	public void addRole(String id, Role role) throws Exception {
		User user = this.userDao.findOne(id);
		Assert.notNull(user);
		user.addRole(role);
		this.saveOrUpdate(user);
	}
	
	/**
	 * 对用户批量赋角色权限，同时更新权限缓存中用户信息
	 * @param id 用户Id
	 * @param roles 角色对象集合
	 */
	@CacheEvict(value="shiroCache",allEntries=true)
	@Transactional(readOnly = false)
	public void addRoles(String id, Set<Role> roles) throws Exception {
		User user = this.findById(id);
		Assert.notNull(user);
		user.setRoles(roles);
		this.saveOrUpdate(user);
	}
	
	/**
	 * 对用户添加或更新组织机构对象
	 * @param id 用户Id
	 * @param group 组织机构
	 */
	@Transactional(readOnly = false)
	public void addOrUpdateGroup(String id, Group group) throws Exception {
		User user = this.userDao.findOne(id);
		Assert.notNull(user);
		if (user.getGroup() == null || user.getGroup().getId() != group.getId()) {
			user.setGroup(group);
		}
		this.saveOrUpdate(user);
	}
	
	/**
	 * 更新用户密码
	 * @param id 用户ID
	 * @param password 密码
	 * @return 更新后的用户对象
	 */
	@Transactional(readOnly = false)
	public User updatePassword(String id, String password) throws Exception {
		Assert.notNull(id);
		User user = this.findById(id);
		user.setPassword(password);
        entryptPassword(user);
		return user;
	}

	/**
	 * 重置用户密码，将用户密码重置为{@code User.DEFAULT_PASSWORD}
	 * @param id 用户ID
	 * @return 重置密码后的用户对象
	 */
	@Transactional(readOnly = false)
	public User resetPassword(String id) throws Exception {
		return updatePassword(id, User.DEFAULT_PASSWORD);
	}
	
	/**
	 * 禁用用户
	 * @param id 用户ID
	 * @return 禁用状态的用户对象
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public User disableUser(String id) throws Exception {
		return changeStatus(id, Status.D);
	}
	
	/**
	 * 启用用户
	 * @param id 用户ID
	 * @return 启用状态的用户对象
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public User enableUser(String id) throws Exception {
		return changeStatus(id, Status.E);
	}

	/**
	 * 更改用户状态
	 * @param id 用户ID
	 * @param status 状态对象
	 * @return 更改状态后的用户对象
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public User changeStatus(String id, Status status) throws Exception {
		Assert.notNull(id);
		User user = this.findById(id);
		user.setStatus(status);
		this.saveOrUpdate(user);
		return user;
	}
	/**
	 * 根据登录名称判断用户是否存在
	 * @param loginName 登录名称
	 * @return 存在返回true，否则返回false
	 */
	public Boolean existLoginName(String loginName) {
		User u = this.userDao.findByLoginName(loginName);
		return u != null;
	}
	
	/**
	 * 根据查询条件查询用户信息列表
	 * @param pageable 分页参数
	 * @param queryVirables 查询参数
	 * @param isContainAdmin 是否包含"admin"对象
	 * @return Page<User> 分页对象
	 */
	public Page<User> findPage(Pageable pageable, Map<String, Object> queryVirables, boolean isContainAdmin) {
		if (!isContainAdmin) {
			if (queryVirables == null) {
				queryVirables = new HashMap<>();
			}
			queryVirables.put("loginName_notEqual", User.ADMIN);
		}
		return this.findPage(pageable, queryVirables);
	}

    /**
     * 对用户密码进行加密，生成随机的salt并经过1024次 sha-1 hash
     */
    private void entryptPassword(User user) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        user.setSalt(EncodeUtils.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, HASH_INTERATIONS);
        user.setPassword(EncodeUtils.encodeHex(hashPassword));
    }

    public List<User> findByText(String text) {
        if (HazeStringUtils.isBlank(text)) {
            Map<String, Object> map  = new HashMap<String, Object>();
            map.put("status", Status.E);
			map.put("group.name_asc", null);
			map.put("sn_asc", null);
			map.put("loginName_notEqual", User.ADMIN);
            return this.findAll(map);
        } else {
            return this.userDao.findByUserNameOrGroupName("%" + text + "%", Status.E);
        }
    }

	public String getName1(String aaa) {
		logger.debug("测试输出" + aaa);
		return "";
	}

	public String getSay(String aaa) {
		logger.debug("测试输出" + aaa);
		return "";
	}
}
