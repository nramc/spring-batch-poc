package com.javaconvergences.batch.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class ConsolePrinterTask implements Tasklet {

	private static Logger log = LoggerFactory.getLogger(ConsolePrinterTask.class);

	public ConsolePrinterTask() {
	}

	public ConsolePrinterTask(List<String> listOfCourses) {
		this.listOfCourses = listOfCourses;
	}

	private List<String> listOfCourses;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		log.info("***************************************************************************************");
		listOfCourses.forEach(c -> log.info(c));
		log.info("***************************************************************************************");
		return RepeatStatus.FINISHED;
	}

}
