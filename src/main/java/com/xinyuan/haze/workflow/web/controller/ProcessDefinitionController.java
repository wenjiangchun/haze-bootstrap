package com.xinyuan.haze.workflow.web.controller;

import com.xinyuan.haze.workflow.exception.DeployFailureException;
import com.xinyuan.haze.workflow.service.ProcessDefinitionService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 流程定义Controller
 * 
 * @author Sofar
 * 
 */
@Controller
@RequestMapping(value = "/workflow/processDefinition")
public class ProcessDefinitionController {

	@Autowired(required=false)
	private ProcessDefinitionService processDefinitionService;

	@RequestMapping(value = "view")
	public String list(Model model, ServletRequest request) {
		List<Object[]> processList = new ArrayList<Object[]>();
		List<ProcessDefinition> processDefinitionList = processDefinitionService
				.getProcessDefinitions();
		for (ProcessDefinition processDefinition : processDefinitionList) {
			Deployment deployment = processDefinitionService
					.getDeploymentByProcessDefinition(processDefinition);
			processList.add(new Object[] { processDefinition, deployment });
		}
		model.addAttribute("processList", processList);
		return "workflow/processDefinition/processDefinitionList";
	}

	/**
	 * 删除流程定义信息
	 */
	@RequestMapping(value = "deleteProcessDefination/{deploymentId}", method = RequestMethod.GET)
	public String deleteProcessDefination(
			@PathVariable("deploymentId") String deploymentId,
			ServletRequest request, RedirectAttributes redirectAttributes) {
		processDefinitionService.deleteDeployment(deploymentId, true);
		redirectAttributes.addFlashAttribute("message", "已删除部署Id为["
				+ deploymentId + "]的流程信息!");
		return "redirect:/workflow/processDefinition/view";
	}

	/**
	 * 更改流程定义信息状态
	 */
	@RequestMapping(value = "updateProcessDefinitionState/{processInstanceId}/{state}", method = RequestMethod.GET)
	public String updateProcessDefinitionState(
			@PathVariable("processInstanceId") String processInstanceId,
			@PathVariable("state") String state, ServletRequest request,
			RedirectAttributes redirectAttributes) {
		if (state.equals("active")) {
			processDefinitionService.activateProcessDefinitionById(
					processInstanceId, true);
			redirectAttributes.addFlashAttribute("message", "已激活ID为["
					+ processInstanceId + "]的流程定义。");
		} else if (state.equals("suspend")) {
			processDefinitionService.suspendProcessDefinitionById(
					processInstanceId, true);
			redirectAttributes.addFlashAttribute("message", "已挂起ID为["
					+ processInstanceId + "]的流程定义。");
		}
		return "redirect:/workflow/processDefinition/view";
	}


	/**
	 *  * 根据部署Id和资源名称获取流程资源信息 其中资源名称可以是流程图片名称或流程定义Xml文件名称
	 * @param deploymentId
	 * @param resourceName
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getProcessResource")
	public void getProcessResource(
			@RequestParam("deploymentId") String deploymentId,
			@RequestParam("resourceName") String resourceName,
			HttpServletResponse response) throws Exception {
		InputStream resourceAsStream = processDefinitionService
				.getProcessResource(deploymentId, resourceName);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}
	
	@RequestMapping(value = "/deployProcessDefinition")
	public String deployProcessDefinition(@Value("${export.diagram.path}") String exportDir, @RequestParam(value = "file", required = false) MultipartFile file) {
		try {
			processDefinitionService.deployProcessDefinition(file, exportDir);
		} catch (DeployFailureException e) {
			e.printStackTrace();
		}
		return "redirect:/workflow/processDefinition/view";
	}
	
}
