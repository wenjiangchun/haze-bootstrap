package com.xinyuan.haze.system.web.controller;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyuan.haze.system.entity.Group;
import com.xinyuan.haze.system.entity.Role;
import com.xinyuan.haze.system.entity.User;
import com.xinyuan.haze.system.service.GroupService;
import com.xinyuan.haze.system.service.RoleService;
import com.xinyuan.haze.system.service.UserService;
import com.xinyuan.haze.system.utils.Sex;
import com.xinyuan.haze.system.utils.Status;
import com.xinyuan.haze.web.ui.datatable.DataTablePage;
import com.xinyuan.haze.web.ui.datatable.DataTableParames;
import com.xinyuan.haze.web.utils.WebMessage;

/**
 * 用户操作Controller
 * @author sofar
 *
 */
@Controller
@RequestMapping(value = "/system/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private GroupService groupService;
	
	@RequestMapping(value = "view")
	public String list(Model model, ServletRequest request) {
		model.addAttribute("statuss", Status.values());
//		model.addAttribute("userTypes", UserType.values());
		String selectGroupId = request.getParameter("groupId");
		model.addAttribute("groupId", selectGroupId);
		return "system/user/userList";
	}
	
	/**
	 * 根据查询参数查询用户列表分页对象
	 * @param dataTableParames 包含分页对象和自定义查询对象的参数,其中PageSize
	 * @param request
	 * @return DataTablePage 前台DataTable组件使用的分页数据对象
	 */
	@RequestMapping(value = "search")
    @ResponseBody
	public DataTablePage search(DataTableParames dataTableParames,ServletRequest request) {
		PageRequest p = dataTableParames.getPageRequest(); //根据dataTableParames对象获取JPA分页查询使用的PageRequest对象
		Map<String, Object> queryVairables = dataTableParames.getQueryVairables(); //获取自定义查询参数
		if (queryVairables != null && queryVairables.get("status") != null) {
			String value = (String) queryVairables.get("status");
            //将传递进来的status字符串转化为Status枚举对象
			queryVairables.put("status", Status.valueOf(value));
		}
		if (queryVairables != null && queryVairables.get("userType") != null) {
			String value = (String) queryVairables.get("userType");
            //将传递进来的status字符串转化为Status枚举对象
//			queryVairables.put("userType", UserType.valueOf(value));
		}
		if (queryVairables.get("group.id") != null) {
			String groupId = (String) queryVairables.get("group.id");
			queryVairables.remove("group.id");
			queryVairables.put("bakGroupId_or_group.id",groupId);
		}

		Page<User> userList = this.userService.findPage(p, queryVairables, false); //过滤掉"admin"对象
		DataTablePage dtp = DataTablePage.generateDataTablePage(userList, dataTableParames); //将查询结果封装成前台使用的DataTablePage对象
		return dtp;
	}

	/**
	 * 进入添加用户页面
	 * @param model
	 * @param request
	 * @return 添加用户页面
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(Model model, ServletRequest request) {
		List<Role> roleList = this.roleService.findByStatus(Status.E); //查找所有启用状态的角色
		List<Group> groupList = this.groupService.findAll();
		model.addAttribute("roleList", roleList);
		model.addAttribute("sexs", Sex.values());
		model.addAttribute("statuss", Status.values());
//		model.addAttribute("userTypes", UserType.values());
		model.addAttribute("groupList",groupList);
		return "system/user/addUser";
	}
	
	/**
	 * 保存用户，同时保存用户角色关联信息
	 * @param user 用户对象
	 * @param roleIds 角色ID集合
	 * @param request
	 * @return 返回用户列表页面
	 */
	@RequestMapping(value = "save")
    @ResponseBody
	public WebMessage save(User user, String[] roleIds, ServletRequest request) {
		Set<Role> roles = new HashSet<Role>();
		if (roleIds != null) {
			for (String roleId : roleIds) {
				Role role = new Role();
				role.setId(roleId);
				roles.add(role);
			}
			if (!roles.isEmpty()) {
				user.setRoles(roles);
			}
		}
		
		if(user.getGroup() != null && StringUtils.isBlank(user.getGroup().getId())){
			user.setGroup(null);
		}
        try {
            this.userService.saveOrUpdate(user);
            return WebMessage.createSuccessWebMessage();
        } catch (Exception e) {
            return WebMessage.createErrorWebMessage(e.getMessage());
        }
    }
	
	@RequestMapping(value = "delete/{ids}")
    @ResponseBody
	public WebMessage delete(@PathVariable("ids") String[] ids, ServletRequest request) {
        try {
            this.userService.batchDelete(ids);
            return WebMessage.createSuccessWebMessage();
        } catch (Exception e) {
            return WebMessage.createErrorWebMessage(e.getMessage());
        }
    }
	
	/**
	 * 进入用户添加角色页面
	 * @param id 用户ID
	 * @param model
	 * @return 用户添加角色页面
	 */
	@RequestMapping(value = "addRoles/{id}", method = RequestMethod.GET)
	public String addRoles(@PathVariable("id")String id, Model model) {
		User user = this.userService.findById(id);
		List<Role> roleList = this.roleService.findByStatus(Status.E);
		Group group = user.getGroup();
		if(group != null){
			model.addAttribute("groupId", group.getId());
		}
		model.addAttribute("user", user);
		roleList.removeAll(user.getRoles());
		model.addAttribute("roleList",roleList);
		return "/system/user/addUserRole";
	}
	
	/**
	 * 对用户进行角色授权
	 * @param roleIds 角色ID集合
	 * @param id 用户ID
	 * @param request
	 * @return 返回用户列表页面
	 */
	@RequestMapping(value = "saveRoles")
    @ResponseBody
	public WebMessage saveRoles(@RequestParam(value="roleIds",required=false) String[] roleIds, @RequestParam("id") String id, ServletRequest request) {
		Set<Role> roles = new HashSet<Role>();
		if (null != roleIds) {
			for (String roleId :roleIds) {
				Role role = new Role();
				role.setId(roleId);
				roles.add(role);
			}
		}
        try {
            this.userService.addRoles(id, roles);
            return WebMessage.createSuccessWebMessage();
        } catch (Exception e) {
            return WebMessage.createErrorWebMessage(e.getMessage());
        }
	}
	
	/**
	 * 更改用户密码
	 * @author 王先先  修改  2013-11-28   先判断用户输入的旧密码是否正确，正确的情况下才可修改密码
	 * @param id    用户id
	 * @param newPassword  新密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updatePassword", method = RequestMethod.POST)	 
	@ResponseBody
	public String updatePassword(@RequestParam(value = "id") String id,@RequestParam(value = "newPassword") String newPassword,ServletRequest request) {
		try {
			this.userService.updatePassword(id, newPassword);
			return WebMessage.ACTION_SUCCESS_MESSAGE;
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@RequestMapping(value = "resetPassword/{id}")
	public WebMessage resetPassword(@PathVariable String id, Model model, ServletRequest request) {
        try {
            this.userService.resetPassword(id);
            return WebMessage.createSuccessWebMessage();
        } catch (Exception e) {
            return WebMessage.createErrorWebMessage(e.getMessage());
        }
    }
	
	/**
	 * 进入用户编辑页面
	 * @param id 用户Id
	 * @param model
	 * @param request
	 * @return 
	 */
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable String id, Model model, ServletRequest request) {
		model.addAttribute("sexs", Sex.values());
		model.addAttribute("statuss", Status.values());
//		model.addAttribute("userTypes", UserType.values());
		User user = this.userService.findById(id);
		List<Group> groupList = this.groupService.findAll();
		model.addAttribute("user", user);
		model.addAttribute("groupList",groupList);
		return "system/user/editUser";
	}
	
	/**
	 * 更新用户信息
	 * @param user 用户
	 * @param request
	 * @return 返回用户列表页面
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public WebMessage update(User user, ServletRequest request) {
		User u = this.userService.findById(user.getId());
		u.setName(user.getName());
		u.setEmail(user.getEmail());
		u.setSex(user.getSex());
		u.setStatus(user.getStatus());
//		u.setUserType(user.getUserType());
		u.setSn(user.getSn());
		u.setSignaturePath(user.getSignaturePath());
		u.setBakGroupId(user.getBakGroupId());
		if (user.getGroup() == null || StringUtils.isEmpty(user.getGroup().getId())) {
			u.setGroup(null);
		} else {
			u.setGroup(user.getGroup());
		}
		/*u.setMobile(user.getMobile());
		u.setTel(user.getTel());*/
        try {
            this.userService.saveOrUpdate(u);
            return WebMessage.createSuccessWebMessage();
        } catch (Exception e) {
            return WebMessage.createErrorWebMessage(e.getMessage());
        }
	}
	
	/**
	 * 判断登录名是否存在
	 * @param loginName 登录名
	 * @return true/false
	 */
	@RequestMapping(value = "isNotExistLoginName", method = RequestMethod.POST)
	@ResponseBody
	public Boolean isNotExistLoginName(String loginName) {
		Boolean isExist = this.userService.existLoginName(loginName);
		return !isExist;
	}	
}
