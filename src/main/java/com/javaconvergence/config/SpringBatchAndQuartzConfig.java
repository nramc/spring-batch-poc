package com.javaconvergence.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.javaconvergences.batch.job.PayrollCalculationJob;
import com.javaconvergences.batch.job.SimpleJob;

@Configuration
public class SpringBatchAndQuartzConfig {

	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private JobLocator jobLocator;

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Bean
	public void jobOneDetail() throws SchedulerException {

		/* Simple job scheduled to print details in console on hourly basis */
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("jobLauncher", jobLauncher);
		jobDataMap.put("jobLocator", jobLocator);

		JobDetail job = JobBuilder.newJob(SimpleJob.class).withIdentity("demoJobOne").setJobData(jobDataMap)
				.storeDurably().build();
		Trigger trigger = TriggerBuilder.newTrigger().forJob(job).withIdentity("jobOneTrigger")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(5).repeatForever()).build();
		schedulerFactoryBean.getScheduler().scheduleJob(job, trigger);

		/*
		 * Payroll calculation job scheduled to calculate salary for Regular employees
		 * on monthly basis
		 */
		JobDataMap payrollJobParam4Regular = new JobDataMap();
		payrollJobParam4Regular.put("jobLauncher", jobLauncher);
		payrollJobParam4Regular.put("jobLocator", jobLocator);
		payrollJobParam4Regular.put("empType", "Regular");
		JobDetail payrollJob4Regular = JobBuilder.newJob(PayrollCalculationJob.class).withIdentity("payrollJob4Regular")
				.setJobData(payrollJobParam4Regular).storeDurably().build();
		Trigger payrollTrigger4Regular = TriggerBuilder.newTrigger().forJob(payrollJob4Regular)
				.withIdentity("payrollTrigger4Regular")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 1 1/1 ? *")).build();
		schedulerFactoryBean.getScheduler().scheduleJob(payrollJob4Regular, payrollTrigger4Regular);

		/*
		 * Payroll calculation job scheduled to calculate salary for Internship
		 * employees on weekly[Saturday] basis
		 */
		JobDataMap payrollJobParam4Internship = new JobDataMap();
		payrollJobParam4Internship.put("jobLauncher", jobLauncher);
		payrollJobParam4Internship.put("jobLocator", jobLocator);
		payrollJobParam4Internship.put("empType", "Internship");
		JobDetail payrollJob4Internship = JobBuilder.newJob(PayrollCalculationJob.class)
				.withIdentity("payrollJob4Internship").setJobData(payrollJobParam4Internship).storeDurably().build();
		Trigger payrollTrigger4Internship = TriggerBuilder.newTrigger().forJob(payrollJob4Internship)
				.withIdentity("payrollTrigger4Internship")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 ? * SAT *")).build();
		schedulerFactoryBean.getScheduler().scheduleJob(payrollJob4Internship, payrollTrigger4Internship);

		/*
		 * Payroll calculation job scheduled to calculate salary for Contract employees
		 * on daily basis
		 */
		JobDataMap payrollJobParam4Contract = new JobDataMap();
		payrollJobParam4Contract.put("jobLauncher", jobLauncher);
		payrollJobParam4Contract.put("jobLocator", jobLocator);
		payrollJobParam4Contract.put("empType", "Internship");
		JobDetail payrollJob4Contract = JobBuilder.newJob(PayrollCalculationJob.class)
				.withIdentity("payrollJob4Contract").setJobData(payrollJobParam4Contract).storeDurably().build();
		Trigger payrollTrigger4Contract = TriggerBuilder.newTrigger().forJob(payrollJob4Contract)
				.withIdentity("payrollTrigger4Contract")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 1/1 * ? *")).build();
		schedulerFactoryBean.getScheduler().scheduleJob(payrollJob4Contract, payrollTrigger4Contract);

		/*
		 * Payroll calculation job scheduled to calculate salary for Freelancer
		 * employees on hourly basis
		 */
		JobDataMap payrollJobParam4Freelancer = new JobDataMap();
		payrollJobParam4Freelancer.put("jobLauncher", jobLauncher);
		payrollJobParam4Freelancer.put("jobLocator", jobLocator);
		payrollJobParam4Freelancer.put("empType", "Internship");
		JobDetail payrollJob4Freelancer = JobBuilder.newJob(PayrollCalculationJob.class)
				.withIdentity("payrollJob4Freelancer").setJobData(payrollJobParam4Freelancer).storeDurably().build();
		Trigger payrollTrigger4Freelancer = TriggerBuilder.newTrigger().forJob(payrollJob4Freelancer)
				.withIdentity("payrollTrigger4Freelancer")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 0/1 1/1 * ? *")).build();
		schedulerFactoryBean.getScheduler().scheduleJob(payrollJob4Freelancer, payrollTrigger4Freelancer);

	}

}
