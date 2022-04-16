package com.method.iot.kafkagrpc.grpc;

import com.method.iot.kafkagrpc.grpc.server.MessageServer;

import java.io.IOException;

public class GRPCApplication {
    public static void main(String[] args) throws IOException {
        MessageServer server = new MessageServer();
        server.startServer();
    }
}
