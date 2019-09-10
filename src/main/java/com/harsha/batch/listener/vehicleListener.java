package com.harsha.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class vehicleListener extends JobExecutionListenerSupport{
	private static final Logger log = LoggerFactory.getLogger(vehicleListener.class);
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus()==BatchStatus.COMPLETED)
		{
			log.info("complted succesfully");
		}
	}
	@Autowired
	public vehicleListener() {
		super();
		// TODO Auto-generated constructor stub
	}
}