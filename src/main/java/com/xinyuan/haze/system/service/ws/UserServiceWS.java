package com.xinyuan.haze.system.service.ws;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface UserServiceWS {

	String getUser(@WebParam(name="id")String id, @WebParam(name="count") int count);
}
