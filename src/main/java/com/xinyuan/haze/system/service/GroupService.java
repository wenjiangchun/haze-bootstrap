package com.xinyuan.haze.system.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xinyuan.haze.core.jpa.service.AbstractBaseService;
import com.xinyuan.haze.core.jpa.service.HazeServiceException;
import com.xinyuan.haze.system.dao.GroupDao;
import com.xinyuan.haze.system.entity.Group;

/**
 * 组织机构业务操作类
 * @author Sofar
 *
 */
@Service
@Transactional
public class GroupService extends AbstractBaseService<Group, String> {

    private GroupDao groupDao;

    @Autowired
	public void setGroupDao(GroupDao groupDao) {
        this.groupDao = groupDao;
		super.setDao(groupDao);
	}
	
	@Transactional(readOnly=true)
	public List<Group> getTopGroups() {
		return groupDao.getTopGroups();
	}

	@Override
	public void delete(Group t) throws HazeServiceException {
        this.groupDao.delete(t);
	}

	@Override
	public void deleteById(String id) throws HazeServiceException {
        this.groupDao.delete(id);
	}

}
