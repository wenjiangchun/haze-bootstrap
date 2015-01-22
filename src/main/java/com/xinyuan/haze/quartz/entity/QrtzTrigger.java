package com.xinyuan.haze.quartz.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.xinyuan.haze.core.jpa.entity.BaseEntity;

@Entity
@Table(name="QRTZ_TRIGGERS")
public class QrtzTrigger implements BaseEntity<QrtzTriggerPK> {

	private static final long serialVersionUID = 1L;
	
	private QrtzTriggerPK id;
	private String description;

	private String endTime;
	
	private byte[] jobData;

	private short misfireInstr;

	private String nextFireTime;

	private String prevFireTime;

	private int priority;

	private String startTime;

	private String triggerState;

	private String triggerType;

	private QrtzJobDetail qrtzJobDetail;

	public QrtzTrigger() {
	}

	@EmbeddedId
	@Override
	public QrtzTriggerPK getId() {
		return this.id;
	}

	@Override
	public void setId(QrtzTriggerPK id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Lob
	public byte[] getJobData() {
		return this.jobData;
	}

	public void setJobData(byte[] jobData) {
		this.jobData = jobData;
	}

	public short getMisfireInstr() {
		return this.misfireInstr;
	}

	public void setMisfireInstr(short misfireInstr) {
		this.misfireInstr = misfireInstr;
	}

	public String getNextFireTime() {
		return this.nextFireTime;
	}

	public void setNextFireTime(String nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	public String getPrevFireTime() {
		return this.prevFireTime;
	}

	public void setPrevFireTime(String prevFireTime) {
		this.prevFireTime = prevFireTime;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getTriggerState() {
		return this.triggerState;
	}

	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
	}

	public String getTriggerType() {
		return this.triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "JOB_GROUP", referencedColumnName = "JOB_GROUP"),
			@JoinColumn(name = "JOB_NAME", referencedColumnName = "JOB_NAME"),
			@JoinColumn(name = "SCHED_NAME", referencedColumnName = "SCHED_NAME") })
	public QrtzJobDetail getQrtzJobDetail() {
		return this.qrtzJobDetail;
	}

	public void setQrtzJobDetail(QrtzJobDetail qrtzJobDetail) {
		this.qrtzJobDetail = qrtzJobDetail;
	}

	/**
	 * 调度表达式
	 */
	private String cronExpression;

	/**
	 * 重复次数
	 */
	private Integer repeatCount;

	/**
	 * 重复间隔时间
	 */
	
	private Integer repeatInteval;

	/**
	 * 重复间隔时间单位
	 */
	
	private RepeateIntevalUnit repeatIntevalUnit;

	@Transient
	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	@Transient
	public Integer getRepeatInteval() {
		return repeatInteval;
	}

	public void setRepeatInteval(Integer repeatInteval) {
		this.repeatInteval = repeatInteval;
	}

	@Transient
	public RepeateIntevalUnit getRepeatIntevalUnit() {
		return repeatIntevalUnit;
	}

	public void setRepeatIntevalUnit(RepeateIntevalUnit repeatIntevalUnit) {
		this.repeatIntevalUnit = repeatIntevalUnit;
	}

	@Override
	public String toString() {
		return null;
	}

}
