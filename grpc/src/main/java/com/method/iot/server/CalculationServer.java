package com.method.iot.server;

import com.method.iot.service.CalculationServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CalculationServer {
    private final int PORT = 1337;

    private Server serverInstance;

    public void startServer() throws IOException {
        serverInstance = ServerBuilder.forPort(PORT)
                .addService(new CalculationServiceImpl())
                .build();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                CalculationServer.this.stopServer();
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
