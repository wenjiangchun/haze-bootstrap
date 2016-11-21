package com.xinyuan.haze.web.controller;

import com.xinyuan.haze.common.utils.ValidateCodeUtils;
import com.xinyuan.haze.core.spring.utils.SpringContextUtils;
import com.xinyuan.haze.security.shiro.ValidateCodeAuthenticationFilter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		//springCamelContextUtils.getRouts();

		//activiti/applicationContext-activiti.xm
		//SpringContextUtils.loadBeanDefinition("camel/applicationContext-camel.xml");
		//SpringCamelContextUtils springCamelContextUtils = SpringContextUtils.getBean(SpringCamelContextUtils.class);
		//springCamelContextUtils.getRouts();
		//SpringContextUtils.loadBeanDefinition("camel/applicationContext-camel.xml");
		try {
			//SpringCamelContextUtils.addRoute(null);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		if (!SecurityUtils.getSubject().isAuthenticated()) {
			return "login";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		return "login";
	}

	@RequestMapping("/validateCode")
	public ResponseEntity<byte[]> validateCode(HttpSession session) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String validateCode = ValidateCodeUtils.getCode(200, 60, 5, outputStream).toLowerCase();
		session.setAttribute(ValidateCodeAuthenticationFilter.DEFAULT_VALIDATE_CODE_PARAM,validateCode);
		byte[] bs = outputStream.toByteArray();
		outputStream.close();
		return new ResponseEntity<byte[]>(bs,headers, HttpStatus.OK);
	}
}
