package com.javaconvergences.batch.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.javaconvergence.entity.Employee;

public class PayrollWriter implements ItemWriter<Employee> {

	private static Logger log = LoggerFactory.getLogger(SimpleJob.class);

	@Override
	public void write(List<? extends Employee> items) throws Exception {

		items.forEach(PayrollWriter::calculatePayroll);

	}

	private static void calculatePayroll(Employee emp) {
		log.info(emp.toString());
		switch (emp.getEmpType()) {
		case "Regular":
			log.info("***************************************************************************************");
			log.info("Payroll calculation for Regular employees will be calculated based on monthly basis" );
			log.info("Salary : {} / month", 31*9*100);
			log.info("***************************************************************************************");
			break;

		case "Internship":
			log.info("***************************************************************************************");
			log.info("Payroll calculation for Internship employees will be calculated based on weekly basis" );
			log.info("Salary : {} / week", 7*9*100);
			log.info("***************************************************************************************");
			break;

		case "Freelancer":
			log.info("***************************************************************************************");
			log.info("Payroll calculation for freelancer employees will be calculated based on hourly basis" );
			log.info("Salary : {} / hour", 1*100);
			log.info("***************************************************************************************");
			break;

		case "Contract":
			log.info("***************************************************************************************");
			log.info("Payroll calculation for Contract employees will be calculated based on daily basis" );
			log.info("Salary : {} / day", 9*100);
			log.info("***************************************************************************************");
			break;

		default:

		}
	}

}
