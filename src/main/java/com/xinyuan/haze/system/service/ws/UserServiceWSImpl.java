package com.xinyuan.haze.system.service.ws;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.xinyuan.haze.system.service.UserService;
import org.springframework.stereotype.Component;

@Component
@WebService(endpointInterface="com.xinyuan.haze.system.service.ws.UserServiceWS")
public class UserServiceWSImpl implements UserServiceWS {

	@Autowired
	private UserService userService;
	
	@Override
	public String getUser(String id, int count) {
		//return userService.findById(id).getLoginName();
        return id + count;
	}

}
