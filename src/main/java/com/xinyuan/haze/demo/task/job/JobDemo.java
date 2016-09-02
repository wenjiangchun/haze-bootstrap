package com.xinyuan.haze.demo.task.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by Sofar on 2016/8/30.
 */
public class JobDemo implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String triggerKey = context.getTrigger().getKey().toString();
        System.out.println("triggerKey="+triggerKey);
    }
}
