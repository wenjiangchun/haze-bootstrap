package com.xinyuan.haze.quartz.entity;

import java.io.Serializable;
import javax.persistence.Column;
import org.quartz.TriggerKey;

public class QrtzTriggerPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "TRIGGER_NAME")
	private String triggerName;

	@Column(name = "TRIGGER_GROUP")
	private String triggerGroup;

	public QrtzTriggerPK() {
	}

	public QrtzTriggerPK(String triggerName, String triggerGroup) {
		super();
		this.triggerName = triggerName;
		this.triggerGroup = triggerGroup;
	}

	public String getTriggerName() {
		return this.triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroup() {
		return this.triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof QrtzTriggerPK)) {
			return false;
		}
		QrtzTriggerPK castOther = (QrtzTriggerPK) other;
		return this.triggerName.equals(castOther.triggerName)
				&& this.triggerGroup.equals(castOther.triggerGroup);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.triggerName.hashCode();
		hash = hash * prime + this.triggerGroup.hashCode();
		return hash;
	}

	/**
	 * 将QrtzTriggerPK对象转换成Quartz中的TriggerKey对象
	 * 
	 * @return JobKey
	 */
	public TriggerKey convertToTriggerKey() {
		return new TriggerKey(triggerName, triggerGroup);
	}
}
