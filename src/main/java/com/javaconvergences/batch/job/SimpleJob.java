package com.javaconvergences.batch.job;

import java.util.Arrays;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Configuration
public class SimpleJob extends QuartzJobBean {

	private static Logger log = LoggerFactory.getLogger(SimpleJob.class);
	
	private JobLauncher jobLauncher;
	private JobLocator jobLocator;

	/*
	 * Quartz scheduler will inject job launcher & locator reference from job param
	 * by using setter methods
	 * 
	 * @Autowire won't help here to inject those references, need to be injected
	 * using job param
	 */
	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

	public void setJobLocator(JobLocator jobLocator) {
		this.jobLocator = jobLocator;
	}

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean("step1ConsolePrinter")
	public Step step1() {
		return stepBuilderFactory.get("step1").tasklet(new ConsolePrinterTask(Arrays.asList("Core Java", "J2EE", "Spring")))
				.build();
	}

	@Bean("step2ConsolePrinter")
	public Step step2() {
		return stepBuilderFactory.get("step2").tasklet(new ConsolePrinterTask(Arrays.asList("Quartz", "Apache Derby", "Log4j")))
				.build();
	}

	@Bean("simpleConsolePrinterJob")
	public Job job() {
		return jobBuilderFactory.get("simpleJob").start(step1()).next(step2()).build();
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {

			if (jobLocator == null || jobLauncher == null)
				throw new Exception("Job Launcher Details is not available");

			JobParametersBuilder params = new JobParametersBuilder();

			params.addDate("time", new Date());

			jobLauncher.run(jobLocator.getJob("simpleJob"), params.toJobParameters());

		} catch (Exception ex) {
			log.error("ERROR:", ex);
		}

	}

}
