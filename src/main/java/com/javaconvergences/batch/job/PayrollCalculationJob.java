package com.javaconvergences.batch.job;

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
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.javaconvergence.entity.Employee;

@Configuration
public class PayrollCalculationJob extends QuartzJobBean {

	private static Logger log = LoggerFactory.getLogger(PayrollCalculationJob.class);

	private JobLauncher jobLauncher;
	private JobLocator jobLocator;

	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

	public void setJobLocator(JobLocator jobLocator) {
		this.jobLocator = jobLocator;
	}

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean("empDetailsCsvReader")
	public FlatFileItemReader<Employee> reader() {
		return new FlatFileItemReaderBuilder<Employee>().name("personItemReader")
				.resource(new ClassPathResource("employee-list.csv")).delimited()
				.names(new String[] { "empId", "firstName", "lastName", "mobile", "empType" })
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {
					{
						setTargetType(Employee.class);
					}
				}).build();
	}

	@Bean("empDetailProcessor")
	@StepScope
	public PayrollProcessor processor() {
		return new PayrollProcessor();
	}

	@Bean("empDetailConsoleWriter")
	public PayrollWriter writer() {
		return new PayrollWriter();
	}

	@Bean("step4RegularEmpPayroll")
	public Step step4RegularEmpPayroll() {
		return stepBuilderFactory.get("step4RegularEmpPayroll").<Employee, Employee>chunk(10).reader(reader()).processor(processor())
				.writer(writer()).build();
	}

	@Bean("job4RegularEmpPayroll")
	public Job job() {
		return jobBuilderFactory.get("job4RegularEmpPayroll").flow(step4RegularEmpPayroll()).end().build();
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {

			if (jobLocator == null || jobLauncher == null)
				throw new Exception("Job Launcher Details is not available");

			String empType = (String) context.getMergedJobDataMap().get("empType");
			JobParametersBuilder params = new JobParametersBuilder();
			params.addDate("time", new Date());
			params.addString("empType", empType);

			
			jobLauncher.run(jobLocator.getJob("job4RegularEmpPayroll"), params.toJobParameters());

		} catch (Exception ex) {
			log.error("ERROR:", ex);
		}

	}

}
