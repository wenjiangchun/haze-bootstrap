package com.xinyuan.haze.demo.task;

import com.xinyuan.haze.file.utils.FTPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 简单任务调度演示类
 * @author sofar
 *
 */
@Component
public class SimpleScheduleDemo extends Thread {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 每天下午4点到5点每分钟执行一次
	 */
    //@Scheduled(cron="${cron}")
	public synchronized  void testCronRun() {
		logger.debug("task demo running at per/1mininus of PM16-PM17");
        FTPUtils.downloadFiles("/home/sofar/下载/ftp", "/447266/ftp");
	}

}
