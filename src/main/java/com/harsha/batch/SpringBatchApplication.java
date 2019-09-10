package com.harsha.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBatchApplication {

	public static void main(String[] args) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		ApplicationContext ctx=SpringApplication.run(SpringBatchApplication.class, args);
		JobLauncher joblauncher= (JobLauncher) ctx.getBean("jobLauncher");
		Job vehicleLauncher=(Job) ctx.getBean("vehicleImportJob");
		Job importUserJob=(Job) ctx.getBean("importUserJob");
		JobParameters jobParameters = 
				  new JobParametersBuilder()
				  .addLong("time",System.currentTimeMillis()).toJobParameters();
		//joblauncher.run(importUserJob, jobParameters);
		joblauncher.run(vehicleLauncher, jobParameters);
		
		
	}

}
