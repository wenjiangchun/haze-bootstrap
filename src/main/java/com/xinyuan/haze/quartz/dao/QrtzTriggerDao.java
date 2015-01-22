package com.xinyuan.haze.quartz.dao;

import org.springframework.stereotype.Repository;

import com.xinyuan.haze.core.jpa.repository.BaseRepository;
import com.xinyuan.haze.quartz.entity.QrtzTrigger;
import com.xinyuan.haze.quartz.entity.QrtzTriggerPK;

@Repository
public interface QrtzTriggerDao extends BaseRepository<QrtzTrigger, QrtzTriggerPK>, QrtzTriggerRepository {

}
