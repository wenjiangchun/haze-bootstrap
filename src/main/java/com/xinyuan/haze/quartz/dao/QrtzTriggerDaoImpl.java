package com.xinyuan.haze.quartz.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class QrtzTriggerDaoImpl implements QrtzTriggerRepository {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findCronTriggers() {
		String sql = "SELECT t.TRIGGER_NAME,t.TRIGGER_GROUP,t.NEXT_FIRE_TIME,t.PREV_FIRE_TIME,t.PRIORITY,t.START_TIME,t.TRIGGER_STATE,t.TRIGGER_TYPE,t.SCHED_NAME,t.JOB_NAME,t.JOB_GROUP,c.CRON_EXPRESSION from QRTZ_TRIGGERS t,QRTZ_CRON_TRIGGERS c where t.SCHED_NAME=c.SCHED_NAME and t.TRIGGER_NAME=c.TRIGGER_NAME and t.TRIGGER_GROUP=c.TRIGGER_GROUP and t.TRIGGER_TYPE='CRON'";
		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findSimpleTriggers() {
		String sql = "SELECT t.TRIGGER_NAME,t.TRIGGER_GROUP,t.NEXT_FIRE_TIME,t.PREV_FIRE_TIME,t.PRIORITY,t.START_TIME,t.TRIGGER_STATE,t.TRIGGER_TYPE,t.SCHED_NAME,t.JOB_NAME,t.JOB_GROUP,s.REPEAT_COUNT,s.REPEAT_INTERVAL,s.TIMES_TRIGGERED from QRTZ_TRIGGERS t,QRTZ_SIMPLE_TRIGGERS s where t.SCHED_NAME=s.SCHED_NAME and t.TRIGGER_NAME=s.TRIGGER_NAME and t.TRIGGER_GROUP=s.TRIGGER_GROUP and t.TRIGGER_TYPE='SIMPLE'";
		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}

}
