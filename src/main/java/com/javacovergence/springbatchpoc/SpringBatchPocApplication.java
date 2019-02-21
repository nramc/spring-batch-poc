package com.javacovergence.springbatchpoc;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan(basePackages= {"com"})
public class SpringBatchPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchPocApplication.class, args);
	}

}
