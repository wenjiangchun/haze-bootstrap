package com.xinyuan.haze.quartz.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyuan.haze.core.jpa.service.AbstractBaseService;
import com.xinyuan.haze.quartz.dao.QrtzJobDetailDao;
import com.xinyuan.haze.quartz.entity.QrtzJobDetail;
import com.xinyuan.haze.quartz.entity.QrtzJobDetailPK;

@Service
public class QrtzJobDetailService extends AbstractBaseService<QrtzJobDetail,QrtzJobDetailPK> {
	
	private QrtzJobDetailDao qrtzJobDetailDao;
	
	@Autowired
	public void setJobDetailDao(QrtzJobDetailDao qrtzJobDetailDao) {
		//this.qrtzJobDetailDao = qrtzJobDetailDao;
		super.setDao(qrtzJobDetailDao);
	}

}
