package com.xinyuan.haze.quartz.dao;

import org.springframework.stereotype.Repository;

import com.xinyuan.haze.core.jpa.repository.BaseRepository;
import com.xinyuan.haze.quartz.entity.QrtzJobDetail;
import com.xinyuan.haze.quartz.entity.QrtzJobDetailPK;

@Repository
public interface QrtzJobDetailDao extends BaseRepository<QrtzJobDetail, QrtzJobDetailPK> {

}
