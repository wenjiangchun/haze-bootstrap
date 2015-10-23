package com.xinyuan.haze.system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.xinyuan.haze.common.utils.HazeStringUtils;
import com.xinyuan.haze.core.jpa.service.AbstractBaseService;
import com.xinyuan.haze.system.dao.ResourceDao;
import com.xinyuan.haze.system.entity.Resource;
import com.xinyuan.haze.system.entity.Role;
import com.xinyuan.haze.system.utils.ResourceType;

/**
 * 资源操作业务类
 * @author sofar
 *
 */
@Service
@Transactional(readOnly = true)
public class ResourceService extends AbstractBaseService<Resource, String>{

	private ResourceDao resourceDao;
	
	@Autowired
	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
		super.setDao(resourceDao);
	}


	/**
	 * 保存或更新资源对象 同时清空shiro缓存对象
	 * @param resource 资源对象
	 * @throws com.xinyuan.haze.core.jpa.service.HazeServiceException
	 */
	@CacheEvict(value="shiroCache",allEntries=true)
	@Transactional(readOnly = false)
	public Resource saveOrUpdate(Resource resource) throws Exception {
		Assert.notNull(resource);
		if (resource.isNew()) { //保存资源对象
			logger.info("save resource：{}", resource);
		} else { //更新资源对象
			logger.info("update resource：{}", resource);
		}
		return this.save(resource);
	}
	
	/**
	 * 获取所有资源权限不为空的权限名称  
	 * @return 资源权限名称集合
	 */
	public List<String> findAllPermission() {
		List<String> permissionList = new ArrayList<String>();
		List<Resource> resourceList = this.resourceDao.findByPermissionIsNotNull();
		for (Resource resource : resourceList) {
			if (HazeStringUtils.isNotEmpty(resource.getPermission())) {
				permissionList.add(resource.getPermission());
			}
		}
		return permissionList;
	}

	/**
	 * 批量删除资源对象 同时清空shiro缓存对象
	 * @param ids 资源ID数组
	 * @throws com.xinyuan.haze.core.jpa.service.HazeServiceException
	 */
	@CacheEvict(value="shiroCache",allEntries=true)
	@Transactional(readOnly = false)
	public void batchDeleteResources(String[] ids) throws Exception {
		for (String id : ids) {
			Resource resource = this.findById(id);
			Set<Role> roles = resource.getRoles();
			for (Role role : roles) {
				role.removeResource(resource);
			}
			this.deleteById(id);
		}
	}
	
	/**
	 * 根据资源对象类型查找资源对象
	 * @param resourceType 资源类型
	 * @return 资源信息列表
	 */
	public List<Resource> findByResourceType(ResourceType resourceType) {
		return this.resourceDao.findByResourceType(resourceType);
	}

	/**
	 * 查找菜单资源
	 * @return 菜单资源信息列表
	 */
	public List<Resource> findMenuResources() {
		return findByResourceType(ResourceType.M);
	}
}
