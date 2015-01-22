package com.xinyuan.haze.quartz.dao;

import java.util.List;

public interface QrtzTriggerRepository {

	/**
	 * 获取CRON类型调度信息
	 * @return Object[]
	 */
	List<Object[]> findCronTriggers();
	
	/**
	 * 获取简单类型调度列表
	 * @return Object[]
	 */
	List<Object[]> findSimpleTriggers();
}
