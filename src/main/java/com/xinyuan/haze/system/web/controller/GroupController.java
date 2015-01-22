package com.xinyuan.haze.system.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

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

import com.xinyuan.haze.core.jpa.service.HazeServiceException;
import com.xinyuan.haze.system.entity.Dictionary;
import com.xinyuan.haze.system.entity.Group;
import com.xinyuan.haze.system.service.DictionaryService;
import com.xinyuan.haze.system.service.GroupService;
import com.xinyuan.haze.web.ui.bootstrap.BootStrapComponentUtils;
import com.xinyuan.haze.web.ui.datatable.DataTablePage;
import com.xinyuan.haze.web.ui.datatable.DataTableParames;

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
	private DictionaryService dictionaryService;
	
	@RequestMapping(value = "view")
	public String list(Model model, ServletRequest request) {
		List<Dictionary> groupTypeList = this.dictionaryService.findChildsByRootCode("groupType");
		model.addAttribute("groupTypeList", groupTypeList);
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
		}
		List<Dictionary> groupTypeList = this.dictionaryService.findChildsByRootCode(Group.GROUP_TYPE);
		model.addAttribute("groupTypeList", groupTypeList);
		return "system/group/addGroup";
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(Group group, ServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
		this.groupService.save(group);
		/*WebMessage message = new WebMessage("机构添加成功", AlertType.SUCCESS);
		redirectAttributes.addFlashAttribute("message", message);*/
		if (group.getParent() != null) {
			redirectAttributes.addFlashAttribute("parentId", group.getParent().getId());
		}
		return "redirect:/system/group/view";
	}
	
	@RequestMapping(value = "delete/{ids}", method = RequestMethod.GET)
	public String delete(@PathVariable("ids") String[] ids, ServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
		this.groupService.batchDelete(ids);
		/*WebMessage alertMessage = new WebMessage("机构删除成功", AlertType.SUCCESS);
		redirectAttributes.addFlashAttribute("message", alertMessage);*/
		return "redirect:/system/group/view";
	}
	
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable String id, Model model, ServletRequest request) {
		Group group = this.groupService.findById(id);
		model.addAttribute("group", group);
		List<Dictionary> groupTypeList = this.dictionaryService.findChildsByRootCode(Group.GROUP_TYPE);
		model.addAttribute("groupTypeList", groupTypeList);
		return "system/group/editGroup";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(Group group, ServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
		this.groupService.save(group);
		/*WebMessage alertMessage = new WebMessage("机构信息更新成功", AlertType.SUCCESS);
		redirectAttributes.addFlashAttribute("message", alertMessage);*/
		if (group.getParent() != null) {
			redirectAttributes.addFlashAttribute("parentId", group.getParent().getId());
		}
		return "redirect:/system/group/view";
	}
}
