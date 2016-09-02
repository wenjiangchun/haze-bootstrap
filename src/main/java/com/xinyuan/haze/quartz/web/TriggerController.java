package com.xinyuan.haze.quartz.web;

import java.util.List;
import java.util.Set;

import javax.servlet.ServletRequest;

import com.xinyuan.haze.web.utils.WebMessage;
import org.quartz.JobKey;
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
import com.xinyuan.haze.quartz.entity.QrtzTrigger;
import com.xinyuan.haze.quartz.entity.RepeateIntevalUnit;
import com.xinyuan.haze.quartz.service.QrtzJobDetailService;
import com.xinyuan.haze.quartz.service.QrtzScheduleService;
import com.xinyuan.haze.quartz.service.QrtzTriggerService;
import com.xinyuan.haze.web.utils.AlertType;

/**
 * 触发器Controller
 * @author sofar
 *
 */
@Controller
@RequestMapping(value = "/quartz/trigger")
public class TriggerController {

	@Autowired
	private QrtzJobDetailService jobDetailService;
	
	@Autowired
	private QrtzTriggerService triggerService;
	
	@Autowired
	private QrtzScheduleService qrtzScheduleService;
	
	@RequestMapping(value = "view")
	public String list(Model model, ServletRequest request) throws SchedulerException {
		List<Object[]> list = triggerService.findAllQuartzTrigger();
		model.addAttribute("list",list);
		return "schedule/trigger/triggerList";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(Model model, ServletRequest request) throws SchedulerException {
		Set<JobKey> jobKeys = this.qrtzScheduleService.findJobKeys();
		model.addAttribute("jobKeys", jobKeys);
		List<QrtzJobDetail> jobDetails = this.jobDetailService.findAll();
		if (jobDetails.isEmpty()) {
			WebMessage message = new WebMessage("暂无作业，请先添加作业", AlertType.ERROR);
			model.addAttribute("message", message);
			return "schedule/trigger/triggerList";
		}
		model.addAttribute("jobDetails", jobDetails);
		model.addAttribute("triggerTypes", RepeateIntevalUnit.values());
		return "schedule/trigger/addTrigger";
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(QrtzTrigger trigger, ServletRequest request, RedirectAttributes redirectAttributes) {
		WebMessage message = new WebMessage();
		try {
			this.qrtzScheduleService.addTrigger(trigger);
			message.setContent("调度添加成功");
			message.setAlertType(AlertType.SUCCESS);
		} catch (SchedulerException e) {
			e.printStackTrace();
			message.setContent("添加失败");
			message.setAlertType(AlertType.ERROR);
		}
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/schedule/trigger/view";
	}
	
	@RequestMapping(value = "pause/{triggerGroup}/{triggerName}", method = RequestMethod.GET)
	public String pause(@PathVariable String triggerGroup, @PathVariable String triggerName, ServletRequest request, RedirectAttributes redirectAttributes) {
		WebMessage message = new WebMessage();
		try {
			this.qrtzScheduleService.pauseTrigger(triggerName, triggerGroup);
			message.setContent("'"+triggerGroup+"."+triggerName+"'已暂停执行");
			message.setAlertType(AlertType.SUCCESS);
		} catch (SchedulerException e) {
			message.setContent("'"+triggerGroup+"."+triggerName+"'暂停执行失败，失败原因："+ e.getMessage());
			message.setAlertType(AlertType.ERROR);
		}
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/schedule/trigger/view";
	}
	
	@RequestMapping(value = "resume/{triggerGroup}/{triggerName}", method = RequestMethod.GET)
	public String resume(@PathVariable String triggerGroup, @PathVariable String triggerName, ServletRequest request, RedirectAttributes redirectAttributes) {
		WebMessage message = new WebMessage();
		try {
			this.qrtzScheduleService.resumeTrigger(triggerName, triggerGroup);
			message.setContent("'"+triggerGroup+"."+triggerName+"'已继续执行");
			message.setAlertType(AlertType.SUCCESS);
		} catch (SchedulerException e) {
			message.setContent("'"+triggerGroup+"."+triggerName+"'继续执行失败，失败原因："+ e.getMessage());
			message.setAlertType(AlertType.ERROR);
		}
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/schedule/trigger/view";
	}
	
	@RequestMapping(value = "delete/{triggerGroup}/{triggerName}", method = RequestMethod.GET)
	public String delete(@PathVariable String triggerGroup, @PathVariable String triggerName, ServletRequest request, RedirectAttributes redirectAttributes) {
		WebMessage message = new WebMessage();
		try {
			this.qrtzScheduleService.deleteTrigger(triggerName, triggerGroup);
			message.setContent("'"+triggerGroup+"."+triggerName+"'已删除");
			message.setAlertType(AlertType.SUCCESS);
		} catch (SchedulerException e) {
			message.setContent("'"+triggerGroup+"."+triggerName+"'删除失败，失败原因："+ e.getMessage());
			message.setAlertType(AlertType.ERROR);
		}
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/schedule/trigger/view";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(Model model, ServletRequest request) throws SchedulerException {
		Set<JobKey> jobKeys = this.qrtzScheduleService.findJobKeys();
		model.addAttribute("jobKeys", jobKeys);
		List<QrtzJobDetail> jobDetails = this.jobDetailService.findAll();
		model.addAttribute("jobDetails", jobDetails);
		model.addAttribute("triggerTypes", RepeateIntevalUnit.values());
		return "schedule/trigger/addTrigger";
	} 
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(Model model, ServletRequest request) throws SchedulerException {
		return "schedule/trigger/addTrigger";
	} 
	
	/**
	 * 判断触发器名称是否存在
	 * @throws SchedulerException 
	 */
	@RequestMapping(value = "isNotExistTriggerName", method = RequestMethod.POST)
	@ResponseBody
	public Boolean isNotExistTriggerName(String triggerName, String triggerGroup) throws SchedulerException {
		Boolean isExist = this.qrtzScheduleService.checkTriggerExists(triggerName, triggerGroup);
		return !isExist;
	}
	
}
