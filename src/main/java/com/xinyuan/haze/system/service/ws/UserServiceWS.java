package com.xinyuan.haze.system.service.ws;

import javax.jws.WebService;

@WebService
public interface UserServiceWS {

	String getUser(String id);
}
