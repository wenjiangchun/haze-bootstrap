package com.xinyuan.haze.system.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.xinyuan.haze.HazeUtils;
import com.xinyuan.haze.common.utils.HazeDateUtils;
import com.xinyuan.haze.security.shiro.CannotAnonymousAccessException;
import com.xinyuan.haze.system.service.UserService;
import com.xinyuan.haze.web.utils.TreeNode;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xinyuan.haze.system.entity.Dictionary;
import com.xinyuan.haze.system.entity.Group;
import com.xinyuan.haze.system.service.DictionaryService;
import com.xinyuan.haze.system.service.GroupService;
import com.xinyuan.haze.web.ui.datatable.DataTablePage;
import com.xinyuan.haze.web.ui.datatable.DataTableParames;
import com.xinyuan.haze.web.utils.WebMessage;

/**
 * 组织机构Controller
 * @author wenjiangchun
 *
 */
@Controller
@RequestMapping(value = "/system/group")
public class GroupController {

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;

	@Autowired
	private DictionaryService dictionaryService;
	
	@RequestMapping(value = "view")
	public String list(Model model, ServletRequest request) {
		String parentId = request.getParameter("parentId");
		List<Dictionary> groupTypeList = this.dictionaryService.findChildsByRootCode(Group.GROUP_TYPE);
		model.addAttribute("groupTypeList", groupTypeList);
		if(StringUtils.isNotBlank(parentId)){
			model.addAttribute("parentId", parentId);
		}
		return "system/group/groupList";
	}
	
	@RequestMapping(value = "search")
	@ResponseBody
	public DataTablePage search(DataTableParames dataTableParames,ServletRequest request) {
		PageRequest p = dataTableParames.getPageRequest(); //根据dataTableParames对象获取JPA分页查询使用的PageRequest对象
		Map<String, Object> map = dataTableParames.getQueryVairables();
		if(map != null && map.get("parent") != null){
			Group g = new Group();
			g.setId((String) map.get("parent"));
			map.put("parent", g);
		} else {
			map.put("parent_isNull", null); //默认查询顶级字典列表
		}
		if (map!= null && map.get("groupType.id") != null) {
			String value = (String) map.get("groupType.id");
			map.put("groupType.id", Long.valueOf(value));
		}
		Page<Group> groupList = this.groupService.findPage(p,dataTableParames.getQueryVairables()); 
		DataTablePage dtp = DataTablePage.generateDataTablePage(groupList, dataTableParames); //将查询结果封装成前台使用的DataTablePage对象
		return dtp;
	}
	
	@RequestMapping(value = "getTopGroups")
	@ResponseBody
	public List<Group> getTopGroups(ServletRequest request, ServletResponse response) {
		List<Group> groups =  groupService.findAll();
		Set<Group> newGroup = new HashSet<Group>();
		Group root = new Group();
		root.setFullName("组织机构树");
		for (Group g : groups) {
			g.setUsers(null);
			g.setChilds(null);
			g.setGroupType(null);
			if (g.getPid() == null) {
				g.setParent(root);
			}
			newGroup.add(g);
		}
		newGroup.add(root);
		return new ArrayList<Group>(newGroup);
	}
	
	/**
	 * 进入添加组织机构页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(@RequestParam(value="parentId",required=false) String parentId, Model model, ServletRequest request) {
		if (parentId != null) {
			Group parent = this.groupService.findById(parentId);
			model.addAttribute("parent", parent);
			model.addAttribute("parentId", parentId);
		}
		List<Dictionary> groupTypeList = this.dictionaryService.findChildsByRootCode(Group.GROUP_TYPE);
		model.addAttribute("groupTypeList", groupTypeList);
		return "system/group/addGroup";
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public WebMessage save(Group group, ServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
		try{
			 this.groupService.save(group);
			 return WebMessage.createSuccessWebMessage();
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return WebMessage.createErrorWebMessage(e.getMessage());
	    }
	}
	
	@RequestMapping(value = "delete/{ids}", method = RequestMethod.GET)
	@ResponseBody
	public WebMessage delete(@PathVariable("ids") String[] ids, ServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
		try{
			this.groupService.batchDelete(ids);
			return WebMessage.createSuccessWebMessage();
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return WebMessage.createErrorWebMessage(e.getMessage());
	    }
	}
	
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable String id, Model model, ServletRequest request) {
		Group group = this.groupService.findById(id);
		model.addAttribute("group", group);
		List<Dictionary> groupTypeList = this.dictionaryService.findChildsByRootCode(Group.GROUP_TYPE);
		model.addAttribute("groupTypeList", groupTypeList);
		if(group.getParent() != null){
			model.addAttribute("parentId", group.getParent().getId());
		}
		
		return "system/group/editGroup";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public WebMessage update(Group group, ServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
		try{
			this.groupService.save(group);
			 return WebMessage.createSuccessWebMessage();
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return WebMessage.createErrorWebMessage(e.getMessage());
	    }
	}
	
	@RequestMapping(value = "getTreeNode", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeNode> getTreeNode() {
		return this.groupService.getTreeNode();
	}

	@RequestMapping(value = "getTreeNodeByUser", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeNode> getTreeNodeByUser() {
		try {
			String userId = HazeUtils.getCurrentUser().getUserId();
			Group g = userService.findById(userId).getGroup();
			return this.groupService.getTreeNode(g.getId());
		} catch (com.xinyuan.haze.security.shiro.CannotAnonymousAccessException e) {
			return this.groupService.getTreeNode();
		}
	}
}
