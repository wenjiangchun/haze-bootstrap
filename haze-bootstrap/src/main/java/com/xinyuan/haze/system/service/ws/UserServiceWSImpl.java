package com.xinyuan.haze.system.service.ws;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.xinyuan.haze.system.service.UserService;

@WebService(endpointInterface="com.xinyuan.haze.system.service.ws.UserServiceWS")
public class UserServiceWSImpl implements UserServiceWS {

	@Autowired
	private UserService userService;
	
	@Override
	public String getUser(String id) {
		return userService.findById(id).getLoginName();
	}

}
