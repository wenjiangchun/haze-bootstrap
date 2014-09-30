package com.xinyuan.haze.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xinyuan.haze.core.jpa.repository.BaseRepository;
import com.xinyuan.haze.system.entity.User;

/**
 * 用户操作接口定义类，采用Spring Data JPA实现其接口中定义方法， UserRepository可以根据实际需要实现复杂业务方法
 *
 * @author sofar
 */
@Repository
public interface UserDao extends BaseRepository<User, String>, UserRepository {

	/**
	 * 根据登陆名获取用户
	 * @param loginName 登陆名称
	 * @return 用户对象
	 */
	User findByLoginName(String loginName);
	
	/**
	 * 查询登录名不包括loginName的用户信息
	 * @param loginName 登录名
	 * @return 用户列表
	 */
	List<User> findByLoginNameNot(String loginName);
	
	/**
	 * 更新用户密码
	 * @param id 用户ID
	 * @param password 密码
	 * @return 更新后用户对象
	 */
	@Modifying
	@Query("update User u set u.password=:password where u.id=:id")
	User updatePassword(@Param("id") Long id, @Param("password") String password);
}
