package com.method.iot.kafkagrpc.grpc.server;

import com.method.iot.kafkagrpc.grpc.service.MessageService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MessageServer {
    private final int PORT = 1337;

    private Server serverInstance;

    public void startServer() throws IOException {
        serverInstance = ServerBuilder.forPort(PORT)
                .addService(new MessageService())
                .build();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                MessageServer.this.stopServer();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
        }));

        serverInstance.start();

        try {
            serverInstance.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
        }
    }

    public void stopServer() throws InterruptedException {
        serverInstance.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }
}