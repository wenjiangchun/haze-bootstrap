package com.xinyuan.haze.workflow.web.controller;

import com.xinyuan.haze.workflow.service.ProcessInstanceService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;
import java.util.List;

/**
 * 流程实例Controller
 * 
 * @author Sofar
 * 
 */
@Controller
@RequestMapping(value = "/workflow/processInstance")
public class ProcessInstanceController {

	@Autowired(required=false)
	private ProcessInstanceService processInstanceService;

	@RequestMapping(value = "view")
	public String list(Model model, ServletRequest request) {
		List<ProcessInstance> processInstanceList = processInstanceService.getRuntimeProcessInstance();
		model.addAttribute("processInstanceList", processInstanceList);
		return "workflow/processInstance/processInstanceList";
	}
	
	/**
	 * 更改流程定义信息状态
	 */
	@RequestMapping(value = "updateProcessInstanceState/{processInstanceId}/{state}", method = RequestMethod.GET)
	public String updateProcessInstanceState(
			@PathVariable("processInstanceId") String processInstanceId,
			@PathVariable("state") String state, ServletRequest request,
			RedirectAttributes redirectAttributes) {
		if (state.equals("active")) {
			processInstanceService.activateProcessInstanceById(
					processInstanceId);
			redirectAttributes.addFlashAttribute("message", "已激活ID为["
					+ processInstanceId + "]的流程实例。");
		} else if (state.equals("suspend")) {
			processInstanceService.suspendProcessInstanceById(
					processInstanceId);
			redirectAttributes.addFlashAttribute("message", "已挂起ID为["
					+ processInstanceId + "]的流程实例。");
		}
		return "redirect:/workflow/processInstance/view";
	}
}
