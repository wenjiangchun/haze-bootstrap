package com.xinyuan.haze.soa.camel.service;

import com.xinyuan.haze.core.jpa.service.AbstractBaseService;
import com.xinyuan.haze.soa.camel.dao.MyRouteDao;
import com.xinyuan.haze.soa.camel.entity.MyRoute;
import com.xinyuan.haze.soa.camel.spring.SpringCamelContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class MyRouteService extends AbstractBaseService<MyRoute, String> {

	private MyRouteDao myRouteDao;

	@Autowired
	public void setMyRouteDao(MyRouteDao myRouteDao) {
		this.myRouteDao = myRouteDao;
		super.setDao(myRouteDao);
	}

	/**
	 *
	 * @param myRoute
	 * @return
	 * @throws Exception
	 */
	@Override
	public MyRoute save(MyRoute myRoute) throws Exception {
		Assert.notNull(myRoute);
		//验证该路由
		//SpringCamelContextUtils.validateRoute(myRoute);
		//SpringCamelContextUtils.addRoute(myRoute);
		return super.save(myRoute);
	}

	@Override
	public void delete(MyRoute myRoute) throws Exception {
		super.delete(myRoute);
	}
}
