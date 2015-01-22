package com.xinyuan.haze.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xinyuan.haze.core.jpa.repository.BaseRepository;
import com.xinyuan.haze.system.entity.Group;

/**
 * 组织机构Dao接口定义
 * @author wenjiangchun
 *
 */
@Repository
public interface GroupDao extends BaseRepository<Group, String> {

	/**
	 * 查询顶级机构信息
	 * @return 顶级机构信息列表
	 */
	@Query("from Group g where g.parent is null")
	List<Group> getTopGroups();

}
