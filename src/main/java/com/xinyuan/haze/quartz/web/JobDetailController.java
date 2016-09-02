package com.xinyuan.haze.quartz.web;

import java.util.List;

import javax.servlet.ServletRequest;

import com.xinyuan.haze.common.utils.HazeClassUtils;
import com.xinyuan.haze.web.utils.WebMessage;
import org.quartz.Job;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xinyuan.haze.quartz.entity.QrtzJobDetail;
import com.xinyuan.haze.quartz.service.QrtzJobDetailService;
import com.xinyuan.haze.quartz.service.QrtzScheduleService;
import com.xinyuan.haze.web.utils.AlertType;

/**
 * 作业定义Controller
 * @author sofar
 *
 */
@Controller
@RequestMapping(value = "/quartz/jobDetail")
public class JobDetailController {

	@Autowired
	private QrtzJobDetailService jobDetailService;
	
	@Autowired
	private QrtzScheduleService qrtzScheduleService;
	
	@RequestMapping(value = "view")
	public String list(Model model, ServletRequest request) throws SchedulerException {
		List<QrtzJobDetail> list = jobDetailService.findAll();
		model.addAttribute("list",list);
		return "schedule/jobDetail/jobDetailList";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(Model model, ServletRequest request) {
		//获取所有Job的子类
		try {
			List<String> jobClassNames = HazeClassUtils.getSubclassNames(Job.class, "com.xinyuan");
			model.addAttribute("jobClassNames", jobClassNames);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "schedule/jobDetail/addJobDetail";
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(QrtzJobDetail qrtzJobDetail, Boolean replace, ServletRequest request, RedirectAttributes redirectAttributes) {
		WebMessage message = new WebMessage();
		try {
			this.qrtzScheduleService.addJobDetail(qrtzJobDetail, true);
			message.setContent("作业添加成功");
			message.setAlertType(AlertType.SUCCESS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			message.setContent("未找到相关Job类");
			message.setAlertType(AlertType.ERROR);
		} catch (SchedulerException e) {
			e.printStackTrace();
			message.setContent("添加失败");
			message.setAlertType(AlertType.ERROR);
		}
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/schedule/jobDetail/view";
	}
	
	@RequestMapping(value = "pause/{jobGroup}/{jobName}", method = RequestMethod.GET)
	public String pause(@PathVariable String jobGroup, @PathVariable String jobName, ServletRequest request, RedirectAttributes redirectAttributes) {
		WebMessage message = new WebMessage();
		try {
			this.qrtzScheduleService.pauseJob(jobName, jobGroup);
			message.setContent("'"+jobGroup+"."+jobName+"'已暂停相关触发器执行");
			message.setAlertType(AlertType.SUCCESS);
		} catch (SchedulerException e) {
			message.setContent("'"+jobGroup+"."+jobName+"'暂停相关触发器执行失败，失败原因："+ e.getMessage());
			message.setAlertType(AlertType.ERROR);
		}
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/schedule/jobDetail/view";
	}
	
	@RequestMapping(value = "resume/{jobGroup}/{jobName}", method = RequestMethod.GET)
	public String resume(@PathVariable String jobGroup, @PathVariable String jobName, ServletRequest request, RedirectAttributes redirectAttributes) {
		WebMessage message = new WebMessage();
		try {
			this.qrtzScheduleService.resumeJob(jobName, jobGroup);
			message.setContent("'"+jobGroup+"."+jobName+"'已继续相关触发器执行");
			message.setAlertType(AlertType.SUCCESS);
		} catch (SchedulerException e) {
			message.setContent("'"+jobGroup+"."+jobName+"'继续执行相关触发器失败，失败原因："+ e.getMessage());
			message.setAlertType(AlertType.ERROR);
		}
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/schedule/jobDetail/view";
	}
	
	@RequestMapping(value = "delete/{jobGroup}/{jobName}", method = RequestMethod.GET)
	public String delete(@PathVariable String jobGroup, @PathVariable String jobName, ServletRequest request, RedirectAttributes redirectAttributes) {
		WebMessage message = new WebMessage();
		try {
			this.qrtzScheduleService.deleteJob(jobName, jobGroup);
			message.setContent("'"+jobGroup+"."+jobName+"'已删除");
			message.setAlertType(AlertType.SUCCESS);
		} catch (SchedulerException e) {
			message.setContent("'"+jobGroup+"."+jobName+"'删除失败，失败原因："+ e.getMessage());
			message.setAlertType(AlertType.ERROR);
		}
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/schedule/jobDetail/view";
	}
	
	/**
	 * 判断作业名称是否存在
	 * @throws SchedulerException 
	 */
	@RequestMapping(value = "isNotExistJobName", method = RequestMethod.POST)
	@ResponseBody
	public Boolean isNotExistJobName(String jobName, String jobGroup) throws SchedulerException {
		Boolean isExist = this.qrtzScheduleService.checkJobExists(jobName, jobGroup);
		return !isExist;
	}
}
