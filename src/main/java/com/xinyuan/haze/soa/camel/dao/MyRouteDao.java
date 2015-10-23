package com.xinyuan.haze.soa.camel.dao;

import com.xinyuan.haze.core.jpa.repository.BaseRepository;
import com.xinyuan.haze.soa.camel.entity.MyRoute;
import com.xinyuan.haze.system.entity.Config;
import org.springframework.stereotype.Repository;

/**
 * camel 路由Dao接口定义类
 *
 * @author sofar
 *
 */
@Repository
public interface MyRouteDao extends BaseRepository<MyRoute, String> {

}
