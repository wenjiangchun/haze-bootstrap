package com.xinyuan.haze.quartz.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xinyuan.haze.common.utils.HazeStringUtils;
import com.xinyuan.haze.quartz.entity.QrtzJobDetail;
import com.xinyuan.haze.quartz.entity.QrtzTrigger;
import com.xinyuan.haze.quartz.entity.RepeateIntevalUnit;
import com.xinyuan.haze.quartz.entity.TriggerType;

/**
 * quartz调度核心Service
 * 
 * @author sofar
 *
 */
@Component
@Transactional(readOnly = false, rollbackFor=SchedulerException.class)
public class QrtzScheduleService {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * quartz默认组名称
	 */
	public static final String DEFAULT_GROUP = Scheduler.DEFAULT_GROUP;

	@Autowired
	private Scheduler scheduler;
	
	/**
	 * 添加作业
	 * @param qrtzJobDetail 作业对象
	 * @param replace 是否替换已存在作业
	 * @throws ClassNotFoundException 如果作业对应Task类不存在则抛出该异常
	 * @throws SchedulerException
	 */
	@SuppressWarnings("unchecked")
	public void addJobDetail(QrtzJobDetail qrtzJobDetail, boolean replace) throws ClassNotFoundException, SchedulerException {
		String jobClassName = qrtzJobDetail.getJobClassName();
		Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(jobClassName);
		String jobName = qrtzJobDetail.getId().getJobName(); 
		String jobGroup = getGroup(qrtzJobDetail.getId().getJobGroup());
		JobBuilder jobBuilder = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup);
		String description = qrtzJobDetail.getDescription();
		if (HazeStringUtils.isNotEmpty(description)) {
			jobBuilder.withDescription(description);
		}
		//jobBuilder.storeDurably(qrtzJobDetail.getIsDurable());
		jobBuilder.storeDurably(true);
		scheduler.addJob(jobBuilder.build(), replace);
		logger.debug("add jobDetail,jobDetail name is {},job Group is {}", jobName, jobGroup);
	}
	
	/**
	 * 判断作业名称是否已存在
	 * @param jobName 作业名称
	 * @param jobGroup 作业分组名称
	 * @return 存在返回true,不存在返回false
	 * @throws SchedulerException
	 */
	@Transactional(readOnly = true)
	public boolean checkJobExists(String jobName, String jobGroup) throws SchedulerException {
		return scheduler.checkExists(new JobKey(jobName, getGroup(jobGroup)));
	}
	
	/**
	 * 删除作业
	 * @param jobName 作业名称
	 * @param jobGroup 作业分组名称
	 * @return 删除成功返回true,否则返回false
	 * @throws SchedulerException
	 */
	public boolean deleteJob(String jobName, String jobGroup) throws SchedulerException {
		jobGroup = getGroup(jobGroup);
		logger.debug("delete jobDetail,jobDetail name is {},job Group is {}", jobName, jobGroup);
		return scheduler.deleteJob(new JobKey(jobName, jobGroup));
	}
	
	/**
	 * 暂停执行作业所有相关触发器调用
	 * @param jobName 作业名称
	 * @param jobGroup 作业组
	 * @throws SchedulerException
	 */
	public void pauseJob(String jobName, String jobGroup) throws SchedulerException {
		jobGroup = getGroup(jobGroup);
		logger.debug("pause jobDetail,jobDetail name is {},job Group is {}", jobName, jobGroup);
	    scheduler.pauseJob(new JobKey(jobName, jobGroup));
	}
	
	/**
	 * 继续执行作业相关触发器调用
	 * @param jobName 作业名称
	 * @param jobGroup 作业组
	 * @throws SchedulerException
	 */
	public void resumeJob(String jobName, String jobGroup) throws SchedulerException {
		jobGroup = getGroup(jobGroup);
		logger.debug("resume jobDetail,jobDetail name is {},job Group is {}", jobName, jobGroup);
	    scheduler.resumeJob(new JobKey(jobName, jobGroup));
	}
	
	/**
	 * 添加作业触发器
	 * @param qrtzTrigger 触发器对象
	 * @throws SchedulerException
	 */
	public void addTrigger(QrtzTrigger qrtzTrigger) throws SchedulerException {
		TriggerBuilder<Trigger> tb = TriggerBuilder.newTrigger();
		String triggerName = qrtzTrigger.getId().getTriggerName();
		String triggerGroup = getGroup(qrtzTrigger.getId().getTriggerGroup());
		tb.withIdentity(triggerName, triggerGroup); //设置triggerKey
		tb.forJob(qrtzTrigger.getQrtzJobDetail().getId().convertToJobKey()); //设置trigger对应JobDetail
		int priority = qrtzTrigger.getPriority(); //获取优先级
		String description = qrtzTrigger.getDescription(); //获取描述信息
		if (priority != 0) { //设置优先级
			tb.withPriority(priority);
		}
		if (HazeStringUtils.isNotEmpty(description)) { //设置trigger描述
			tb.withDescription(description);
		}
		String triggerType = qrtzTrigger.getTriggerType();
		if (triggerType.equals(TriggerType.CRON.name())) { //创建CronSchedule对象
			String cronExpression = qrtzTrigger.getCronExpression();
			CronScheduleBuilder cb = CronScheduleBuilder.cronSchedule(cronExpression);
			tb.withSchedule(cb);
		} else { //创建simpleSchedule对象
			SimpleScheduleBuilder sb = SimpleScheduleBuilder.simpleSchedule(); 
			RepeateIntevalUnit repeatIntevalType = qrtzTrigger.getRepeatIntevalUnit(); //获取重复执行时间单位
			Integer repeatInteval = qrtzTrigger.getRepeatInteval();
			if (repeatIntevalType != null && repeatInteval != null) {
				if (RepeateIntevalUnit.HOUR == repeatIntevalType) {
					sb.withIntervalInHours(repeatInteval); //每间隔repeatInteval小时执行
				} else if (RepeateIntevalUnit.MINUTE == repeatIntevalType) {
					sb.withIntervalInMinutes(repeatInteval); //每间隔repeatInteval分钟执行
				} else if (RepeateIntevalUnit.SECOND == repeatIntevalType) {
					sb.withIntervalInSeconds(repeatInteval); //每间隔repeatInteval秒执行
				} else {
					sb.withIntervalInMilliseconds(repeatInteval); //每间隔repeatInteval毫秒执行
				}
			}
			String startTime = qrtzTrigger.getStartTime(); //获取开始执行时间
			String endTime = qrtzTrigger.getEndTime(); //获取结束执行时间
			if (HazeStringUtils.isNotEmpty(startTime)) { //设置开始执行时间 如果未设置则表示即时执行
				try {
					tb.startAt(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startTime));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if (HazeStringUtils.isNotEmpty(endTime)) { //设置结束执行时间  如果未设置则表示永远执行
				try {
					tb.endAt(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startTime));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			Integer repeatCount = qrtzTrigger.getRepeatCount();
			sb.withRepeatCount(repeatCount);  //重复次数
			tb.withSchedule(sb); 
		}
		logger.debug("add trigger,trigger name is {},trigger Group is {}", triggerName, triggerGroup);
		scheduler.scheduleJob(tb.build());
	}
	
	/**
	 * 更新触发器
	 * @param qrtzTrigger 触发器对象
	 * @throws SchedulerException
	 */
	public void updateTrigger(QrtzTrigger qrtzTrigger) throws SchedulerException {
		scheduler.unscheduleJob(qrtzTrigger.getId().convertToTriggerKey());
		addTrigger(qrtzTrigger);
	}
	
	/**
	 * 删除触发器
	 * @param triggerName 触发器名称
	 * @param triggerGroup 触发器所在分组名称
	 * @return 删除成功返回true 否则返回false
	 * @throws SchedulerException
	 */
	public boolean deleteTrigger(String triggerName, String triggerGroup) throws SchedulerException {
		logger.debug("delete trigger,trigger name is {},trigger Group is {}", triggerName, triggerGroup);
		return scheduler.unscheduleJob(new TriggerKey(triggerName, getGroup(triggerGroup)));
	}
	/**
	 * 获取当前运行的JobExecutionContext
	 * @return JobExecutionContext列表
	 * @throws SchedulerException
	 */
	@Transactional(readOnly = true)
	public List<JobExecutionContext> getCurrentlyExecutingJobs() throws SchedulerException {
		return scheduler.getCurrentlyExecutingJobs();
	}
	
	/**
	 * 暂停执行触发器调用
	 * @param triggerKeyName 触发器名称
	 * @param triggerGroup 触发器分组名称
	 * @throws SchedulerException
	 */
	public void pauseTrigger(String triggerName, String triggerGroup) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(triggerName, getGroup(triggerGroup));
		scheduler.pauseTrigger(triggerKey);
		logger.debug("pause trigger,trigger name is {},trigger Group is {}", triggerName, triggerGroup);
	}
	
	/**
	 * 继续执行触发器调用
	 * @param triggerKeyName 触发器名称
	 * @param triggerGroup 触发器分组名称
	 * @throws SchedulerException
	 */
	public void resumeTrigger(String triggerName, String triggerGroup) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(triggerName, getGroup(triggerGroup));
		scheduler.resumeTrigger(triggerKey);
		logger.debug("resume trigger,trigger name is {}", triggerName);
	}
	
	/**
	 * 判断触发器名称是否已存在
	 * @param triggerName 触发器名称
	 * @param triggerGroup 触发器分组名称
	 * @return 存在返回true,不存在返回false
	 * @throws SchedulerException
	 */
	@Transactional(readOnly = true)
	public boolean checkTriggerExists(String triggerName, String triggerGroup) throws SchedulerException {
		return scheduler.checkExists(new TriggerKey(triggerName, getGroup(triggerGroup)));
	}

	/**
	 * 获取分组名称 
	 * @param group 分组名称
	 * @return 如果传递进来的参数为null或空字符串，则返回"DEFAULT",否则返回参数组名称
	 */
	private String getGroup(String group) {
		return HazeStringUtils.isEmpty(group) ? DEFAULT_GROUP : group ;
	}

	/**
	 * 获取作业组下所有JobKey 
	 * @param groupName 作业组名称
	 * @return Set<JobKey> JobKey Set集合
	 * @throws SchedulerException
	 */
	public Set<JobKey> findJobKeys(String groupName) throws SchedulerException {
		return scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
	}
	
	/**
	 * 获取DEFAULT作业组下所有JobKey
	 * @return Set<JobKey> JobKey Set集合
	 * @throws SchedulerException
	 */
	public Set<JobKey> findJobKeys() throws SchedulerException {
		return findJobKeys(DEFAULT_GROUP);
	}
}