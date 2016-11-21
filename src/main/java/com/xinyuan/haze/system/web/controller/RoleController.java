package com.xinyuan.haze.system.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyuan.haze.system.entity.Resource;
import com.xinyuan.haze.system.entity.Role;
import com.xinyuan.haze.system.exception.RoleExistException;
import com.xinyuan.haze.system.service.ResourceService;
import com.xinyuan.haze.system.service.RoleService;
import com.xinyuan.haze.system.utils.Status;
import com.xinyuan.haze.web.ui.datatable.DataTablePage;
import com.xinyuan.haze.web.ui.datatable.DataTableParams;
import com.xinyuan.haze.web.utils.WebMessage;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 角色管理Controller
 * @author sofar
 *
 */
@Controller
@RequestMapping(value = "/system/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping(value = "view")
	public String list(Model model, ServletRequest request) {
		model.addAttribute("statuss", Status.values());
		return "system/role/roleList";
	}
	
	@RequestMapping(value = "search")
	@ResponseBody
	public DataTablePage search(DataTableParams dataTableParams, ServletRequest request) {
		PageRequest p = dataTableParams.getPageRequest();
		Map<String, Object> queryVaribles = dataTableParams.getQueryVairables();
		if (queryVaribles != null && queryVaribles.get("status") != null) {
			String value = (String) queryVaribles.get("status");
			queryVaribles.put("status", Status.valueOf(value));
		}
		Page<Role> roleList = this.roleService.findPage(p, dataTableParams.getQueryVairables());
		DataTablePage dtp = DataTablePage.generateDataTablePage(roleList, dataTableParams);
		return dtp;
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(Model model, ServletRequest request) {
		model.addAttribute("statuss", Status.values());
		return "system/role/addRole";
	}
	
	@RequestMapping(value = "save")
    @ResponseBody
	public WebMessage save(Role role, HttpServletRequest request) {
		try {
			this.roleService.saveOrUpdate(role);
            return WebMessage.createSuccessWebMessage();
		} catch (Exception e) {
			return WebMessage.createErrorWebMessage(e.getMessage());
		}
	}
	
	@RequestMapping(value = "delete/{ids}")
    @ResponseBody
	public WebMessage delete(@PathVariable("ids") String[] ids, HttpServletRequest request) {
        try {
            this.roleService.batchDelete(ids);
            return WebMessage.createSuccessWebMessage();
        } catch (Exception e) {
            return WebMessage.createErrorWebMessage(e.getMessage());
        }
	}
	
	/**
	 * 进入对角色添加资源管理权限页面
	 * @param id 角色Id
	 * @param model
	 * @param request
	 * @return 
	 */
	@RequestMapping(value = "addResources/{id}", method = RequestMethod.GET)
	public String addResources(@PathVariable("id") String id, Model model, ServletRequest request) {
		Role role = this.roleService.findById(id);
		List<Resource> menus = this.resourceService.findMenuResources();
		List<Resource> newMenus = new ArrayList<Resource>();
		model.addAttribute("role",role);
		model.addAttribute("menus", menus);
		return "system/role/addResource";
	}
	
	/**
	 * 对角色添加资源管理权限
	 * @param id 角色Id
	 * @param resourceIds 系统资源Id数组
	 * @return 返回列表页面
	 */
	@RequestMapping(value = "saveResources/{roleId}/{resourceIds}")
    @ResponseBody
	public WebMessage saveResources(@PathVariable("roleId") String id,@PathVariable("resourceIds") String[] resourceIds, HttpServletRequest request) {
        try {
            this.roleService.addResources(id, resourceIds);
            return WebMessage.createSuccessWebMessage();
        } catch (Exception e) {
            return WebMessage.createErrorWebMessage(e.getMessage());
        }
    }
	
	/**
	 * 进入角色编辑页面
	 * @param id 用户Id
	 * @param model
	 * @param request
	 * @return 
	 */
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable String id, Model model, HttpServletRequest request) {
		model.addAttribute("statuss", Status.values());
		Role role = this.roleService.findById(id);
		model.addAttribute("role", role);
		return "system/role/editRole";
	}
	
	/**
	 * 更新角色信息
	 * @param role 角色信息
	 * @param request
	 * @return 返回角色列表页面
	 */
	@RequestMapping(value = "update")
    @ResponseBody
	public WebMessage update(Role role, HttpServletRequest request) {
		Role r = this.roleService.findById(role.getId());
		r.setName(role.getName());
		r.setStatus(role.getStatus());
		try {
			this.roleService.saveOrUpdate(r);
            return WebMessage.createLocaleSuccessWebMessage(RequestContextUtils.getLocale(request));
		} catch (RoleExistException e) {
            return WebMessage.createErrorWebMessage(e.getMessage(), RequestContextUtils.getLocale(request));
		}
	}
	
	/**
	 * 判断角色英文名是否存在
	 * @param roleName 角色英文名称
	 * @return true/false 不存在返回true,否则返回false
	 */
	@RequestMapping(value = "notExistRoleName", method = RequestMethod.POST)
	@ResponseBody
	public Boolean notExistRoleName(String roleName) {
		Boolean isExist = this.roleService.existCode(roleName);
		return !isExist;
	}
}
