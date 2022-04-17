package com.method.iot.webapp;

import com.method.iot.webapp.broker.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;

@SpringBootApplication
public class WebappApplication {

	@Autowired
	private MessageListener messageListener;

	@Bean
	public CommandLineRunner schedulingRunner(TaskExecutor executor) {
		return args -> executor.execute(messageListener);
	}

	@Bean
	public MessageListener service() {
		return new MessageListener();
	}

	public static void main(String[] args) throws Exception {
//		MqttClient c = new MqttClient("tcp://broker.mqttdashboard.com:1883", UUID.randomUUID().toString(), new MemoryPersistence());
//		MqttConnectOptions connectOptions = new MqttConnectOptions();
//		connectOptions.setCleanSession(true);
//		c.connect(connectOptions);
		SpringApplication.run(WebappApplication.class, args);
	}

}
