package com.xinyuan.haze.system.dao;

import org.springframework.stereotype.Repository;

import com.xinyuan.haze.core.jpa.repository.BaseRepository;
import com.xinyuan.haze.system.entity.Config;

/**
 * 系统配置Dao接口定义类
 *
 * @author sofar
 *
 */
@Repository
public interface ConfigDao extends BaseRepository<Config, String> {

}
