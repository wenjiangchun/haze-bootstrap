package schedule.job

import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

class GroovyHelloWordJob {
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		println "定时调用";
	}
}
