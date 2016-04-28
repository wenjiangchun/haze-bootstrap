package com.xinyuan.haze.workflow.service;

import com.xinyuan.haze.workflow.exception.DeployFailureException;
import com.xinyuan.haze.workflow.utils.WorkflowUtils;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * 流程定义Service
 * @author Sofar
 *
 */
@Service
@Transactional(readOnly = true)
public class ProcessDefinitionService {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired(required=false)
	private RepositoryService repositoryService;
	
	@Autowired(required=false)
	private RuntimeService runtimeService;

	@Autowired(required=false)
	private IdentityService identityService;
	
	@Autowired(required=false)
	private TaskService taskService;
	
	/**
	 * 获取流程定义信息
	 * @return 流程定义集合
	 */
	public List<ProcessDefinition> getProcessDefinitions() {
		return repositoryService.createProcessDefinitionQuery().list();
	}
	
	/**
	 * 获取激活状态的流程定义信息
	 * @return 激活状态的流程定义集合
	 */
	public List<ProcessDefinition> getActiveProcessDefinitons() {
		return repositoryService.createProcessDefinitionQuery().active().list();
	}

	/**
	 * 获取流程定义部署信息
	 * @param processDefinition 流程定义
	 * @return 流程部署信息
	 */
	public Deployment getDeploymentByProcessDefinition(ProcessDefinition processDefinition) {
		String deploymentId = processDefinition.getDeploymentId();
	    Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
		return deployment;
	}
	
	/**
	 * 部署流程定义信息
	 * @param file 流程文件
	 * @param exportDir 导出路径
	 * @throws DeployFailureException  部署失败
	 */
	@Transactional(readOnly = false)
	public void deployProcessDefinition(MultipartFile file, String exportDir) throws DeployFailureException {
		String fileName = file.getOriginalFilename();
	    try {
	      InputStream fileInputStream = file.getInputStream();
	      Deployment deployment = null;
	      String extension = FilenameUtils.getExtension(fileName);
	      if (extension.equals("zip") || extension.equals("bar")) {
	        ZipInputStream zip = new ZipInputStream(fileInputStream);
	        deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
	      } else {
	        deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
	      }
	      List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
	      for (ProcessDefinition processDefinition : list) {
	        WorkflowUtils.exportDiagramToFile(repositoryService, processDefinition, exportDir);
	      }
	    } catch (Exception e) {
	    	logger.error("error on deploy process, because of file input stream", e);
	        throw new DeployFailureException("部署失败");
	    }
	}

	/**
	 * 删除流程部署信息
	 * @param deploymentId 流程部署Id
	 * @param b
	 */
	public void deleteDeployment(String deploymentId, boolean b) {
		repositoryService.deleteDeployment(deploymentId, b);
	}
	
	/**
	 * 根据流程定义id和状态激活流程信息
	 * @param processDefinitionId 流程定义ID
	 * @param b 流程定义状态
	 */
	@Transactional(readOnly = true)
	public void activateProcessDefinitionById(String processDefinitionId,
			boolean b) {
		repositoryService.activateProcessDefinitionById(processDefinitionId, b, null);
	}
	
	/**
	 * 根据流程定义id和状态挂起流程信息
	 * <p>当挂起流程定义信息后，该流程定义下所有流程实例同时状态改变为挂起</p>
	 * @param processDefinitionId 流程定义ID
	 * @param b 流程定义状态
	 */
	@Transactional(readOnly = true)
	public void suspendProcessDefinitionById(String processDefinitionId,
			boolean b) {
		repositoryService.suspendProcessDefinitionById(processDefinitionId, b, null);
	}

	/**
	 * 根据流程部署Id和资源名称获取资源信息
	 * @param deploymentId 流程部署Id
	 * @param resourceName 资源名称 可以为Xml文件名和流程图片名称
	 * @return InputStream
	 */
	public InputStream getProcessResource(String deploymentId,
			String resourceName) {
		return repositoryService.getResourceAsStream(deploymentId, resourceName);
	}
}
