package com.xinyuan.haze.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xinyuan.haze.core.jpa.repository.BaseRepository;
import com.xinyuan.haze.system.entity.Role;
import com.xinyuan.haze.system.utils.Status;

/**
 * 角色操作接口定义类
 * 
 * @author sofar
 *
 */
@Repository
public interface RoleDao extends BaseRepository<Role, String> {

	/**
	 * 根据角色状态查找角色对象
	 * @param status 状态值
	 * @return 角色对象集合
	 */
	List<Role> findByStatus(Status status);

	/**
	 * 根据角色英文名称查找角色对象
	 * @param code 角色代码
	 * @return 角色对象
	 */
	Role findByCode(String code);
	
}
