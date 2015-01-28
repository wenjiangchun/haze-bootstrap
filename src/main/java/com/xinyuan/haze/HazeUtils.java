package com.xinyuan.haze;

import org.apache.shiro.SecurityUtils;

import com.xinyuan.haze.security.shiro.CannotAnonymousAccessException;
import com.xinyuan.haze.security.shiro.ShiroUser;
import com.xinyuan.haze.system.entity.User;

/**
 * 系统辅助工具类
 * @author Sofar
 *
 */
public class HazeUtils {

	private static final String ADMIN = "admin";

	/**
	 * 获取当前登陆用户
	 * @return 当前用户
	 * @throws Exception 
	 */
	public static ShiroUser getCurrentUser() throws CannotAnonymousAccessException {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (user == null) { 
			throw new CannotAnonymousAccessException("找不到当前用户，请确保用户已登陆系统");
		}
		return user;
	}
	
	/**
	 * 判断用户是否为超级管理员
	 * @param user
	 * @return
	 */
	public static boolean isSuperAdmin(User user) {
		return user.getLoginName().equals(ADMIN);
	}

}
