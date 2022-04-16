package com.method.iot.kafkagrpc.web;

import com.method.iot.kafkagrpc.MessageServiceGrpc;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebApplication {

	@Value("${grpc.server.address}")
	private String serviceAddress;

	@Value("${grpc.server.port}")
	private String servicePort;

	@Bean
	public MessageServiceGrpc.MessageServiceBlockingStub grpcMessageService() {
		return MessageServiceGrpc.newBlockingStub(
				ManagedChannelBuilder
						.forAddress(serviceAddress, Integer.parseInt(servicePort))
						.usePlaintext()
						.build()
		);
	}

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

}
