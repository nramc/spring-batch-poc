package com.javaconvergences.batch.job;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import com.javaconvergence.entity.Employee;

@Scope(value = "step")
public class PayrollProcessor implements ItemProcessor<Employee, Employee> {

	private static Logger log = LoggerFactory.getLogger(PayrollProcessor.class);

	@Value("#{jobParameters['empType']}")
	private String empType;

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	@Override
	public Employee process(Employee emp) throws Exception {

		boolean flg = StringUtils.equalsIgnoreCase(empType, emp.getEmpType()) || "ALL".equalsIgnoreCase(empType);
		if (flg) {
			log.info("***************************************************************************************");
			log.info("ID:[{}] First Name: [{}] Last Name:[{}] Mobile:[{}]", emp.getEmpId(), emp.getFirstName(),
					emp.getLastName(), emp.getMobile());
			log.info("***************************************************************************************");
		}
		return flg ? emp : null;
	}

}
