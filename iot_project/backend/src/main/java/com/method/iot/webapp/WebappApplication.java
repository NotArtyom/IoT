package com.method.iot.webapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.method.iot.webapp.broker.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class WebappApplication {

	@Autowired
	private MessageListener messageListener;

	@Primary
	@Bean
	public TaskExecutor primaryTaskExecutor() {
		return new ThreadPoolTaskExecutor();
	}

	@Bean
	public CommandLineRunner schedulingRunner(TaskExecutor executor) {
		return args -> executor.execute(messageListener);
	}

	@Bean
	public MessageListener service() {
		return new MessageListener();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(WebappApplication.class, args);
	}

}
