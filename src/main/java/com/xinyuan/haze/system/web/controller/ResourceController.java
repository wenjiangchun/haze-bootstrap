package com.xinyuan.haze.system.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xinyuan.haze.system.entity.Resource;
import com.xinyuan.haze.system.service.ResourceService;
import com.xinyuan.haze.system.utils.ResourceType;
import com.xinyuan.haze.web.ui.datatable.DataTablePage;
import com.xinyuan.haze.web.ui.datatable.DataTableParams;
import com.xinyuan.haze.web.utils.WebMessage;

/**
 * 资源操作Controller
 * @author Sofar
 *
 */
@Controller
@RequestMapping(value = "/system/resource")
public class ResourceController {
	
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping(value = "view")
	public String list(Model model, ServletRequest request) {
		model.addAttribute("resourceTypes",ResourceType.values());
		return "system/resource/resourceList";
	}
	
	@RequestMapping(value = "search")
	@ResponseBody
	public DataTablePage search(DataTableParams dataTableParams, ServletRequest request) {
		PageRequest p = dataTableParams.getPageRequest();
		Map<String, Object> queryVaribles = dataTableParams.getQueryVairables();
		if (queryVaribles != null && queryVaribles.get("resourceType") != null) {
			String value = (String) queryVaribles.get("resourceType");
			queryVaribles.put("resourceType",ResourceType.valueOf(value));
		}
		Page<Resource> resourceList = this.resourceService.findPage(p, dataTableParams.getQueryVairables());
		DataTablePage dtp = DataTablePage.generateDataTablePage(resourceList, dataTableParams);
		return dtp;
	}
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(Model model, ServletRequest request) {
		model.addAttribute("resourceTypes",ResourceType.values());
		List<Resource> resources = this.resourceService.findMenuResources();
		model.addAttribute("resources",resources);
		return "system/resource/addResource";
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public WebMessage save(Resource resource, ServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
		if (resource.getParent() != null && StringUtils.isBlank(resource.getParent().getId())) {
			resource.setParent(null);
		}
		try {
			this.resourceService.saveOrUpdate(resource);
            return WebMessage.createSuccessWebMessage();
        } catch (Exception e) {
            return WebMessage.createErrorWebMessage(e.getMessage());
        }
		//return "redirect:/system/resource/view";
	}
	
	@RequestMapping(value = "getResources")
	@ResponseBody
	public List<TreeNode> getResources(ServletRequest request, ServletResponse response) {
		List<Resource> resourceList = this.resourceService.findAll();
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		for (Resource resource : resourceList) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(resource.getId());
			treeNode.setName(resource.getName());
			treeNode.setParentId(resource.getParent() != null ? resource.getParent().getId() : null);
			treeNodeList.add(treeNode);
		}
		return treeNodeList;
	}
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable String id, Model model, ServletRequest request) {
		model.addAttribute("resourceTypes",ResourceType.values());
		List<Resource> menuResources = new ArrayList<Resource>();
		Resource resource = this.resourceService.findById(id);
		if (resource.getParent() != null) {
			menuResources = this.resourceService.findMenuResources();
		}
		model.addAttribute("resource",resource);
		model.addAttribute("menuResources",menuResources);
		return "system/resource/editResource";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(Resource resource, ServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
		if (resource.getParent() != null && resource.getParent().getId() == null) {
			resource.setParent(null);
		}
		this.resourceService.saveOrUpdate(resource);
		/*WebMessage message = new WebMessage("资源更新成功", AlertType.SUCCESS);
		redirectAttributes.addFlashAttribute("message", message);*/
		return "redirect:/system/resource/view/";
	}
	
	@RequestMapping(value = "delete/{ids}", method = RequestMethod.GET)
	public String delete(@PathVariable("ids") String[] ids, ServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
		this.resourceService.batchDeleteResources(ids);
		/*WebMessage message = new WebMessage("资源删除成功", AlertType.SUCCESS);
		redirectAttributes.addFlashAttribute("message", message);*/
		return "redirect:/system/resource/view/";
	}
}
