package com.xinyuan.haze.web.controller;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.RuntimeMXBean;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xinyuan.haze.core.spring.utils.SpringContextUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.xinyuan.haze.common.utils.HazeDateUtils;
import com.xinyuan.haze.system.entity.Resource;
import com.xinyuan.haze.system.service.ResourceService;

/**
 * 系统 Home Controller
 * 
 */
@Controller
public class HomeController {

	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@SuppressWarnings("restriction")
	@RequestMapping(value = "/")
	public String index(HttpServletRequest request, HttpServletResponse response) {
        String me = SpringContextUtils.getMessage("fsd", null,RequestContextUtils.getLocale(request));
        Properties p = System.getProperties();
		request.setAttribute("properties", p);
		long maxMemory = Runtime.getRuntime().maxMemory();
		long freeMemory = Runtime.getRuntime().freeMemory();
		long totalMemory = Runtime.getRuntime().totalMemory();
		request.setAttribute("maxMemory", maxMemory/(1024*1024));
		request.setAttribute("freeMemory", freeMemory/(1024*1024));
		request.setAttribute("totalMemory", totalMemory/(1024*1024));
		RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
		request.setAttribute("vmName", System.getProperty("java.version"));
		request.setAttribute("vmVersion", bean.getVmVersion());
		request.setAttribute("startTime", HazeDateUtils.format(new Date(bean.getStartTime())));
		
		request.setAttribute("upTime", HazeDateUtils.format(new Date(bean.getUptime())));
		OperatingSystemMXBean mx = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		request.setAttribute("mx", mx);
		request.setAttribute("totalPhysicalMemory", mx.getTotalPhysicalMemorySize()/(1024*1024));
		request.setAttribute("freePhysicalMemory", mx.getFreePhysicalMemorySize()/(1024*1024));
		return "index";
	}
	
	@RequestMapping(value = "/changeLanguage", method = RequestMethod.POST)
	@ResponseBody
	public String changeLanguage(String language, HttpServletRequest request,
			HttpServletResponse response) {
		String message = "";
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		if (localeResolver == null) {
			throw new IllegalStateException(
					"No LocaleResolver found: not in a DispatcherServlet request?");
		}
		LocaleEditor localeEditor = new LocaleEditor();
		localeEditor.setAsText(language);
		localeResolver.setLocale(request, response,
				(Locale) localeEditor.getValue());
		message = "Change Language Success!";
		return message;
	}
	
    @RequestMapping(value ="/changeTheme", method = RequestMethod.POST)  
    public void changeTheme(HttpServletRequest request, HttpServletResponse response, String theme) { 
    	ThemeResolver themeResolver = RequestContextUtils.getThemeResolver(request);
        themeResolver.setThemeName(request, response, theme);  
    }  
    
    @RequestMapping(value ="/left")
    public String getMenuResource(Model model) {
    	List<Resource> resources = resourceService.findMenuResources();
    	model.addAttribute("resources", resources);
    	return "left";
    }
    
    /**
     * 获取在线用户
     * @param model
     * @return 在线用户集合
     */
    @RequestMapping(value ="/getOnlineUser")
    public Set<String> getOnlineUser(Model model) {
    	Collection<Session> sessions = sessionDAO.getActiveSessions();
		HashSet<String> onlineUsers = new HashSet<String>();
		for (Session session : sessions) {
			Collection<Object> s = session.getAttributeKeys();
			for (Object o : s) {
				System.out.println(o+"="+session.getAttribute(o));
			}
			if (session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY") != null) {
				onlineUsers.add(session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY").toString());
			}
		}
		return onlineUsers;
    }
}
