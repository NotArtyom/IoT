package com.method.iot;

import com.method.iot.server.CalculationServer;

import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        CalculationServer server = new CalculationServer();
        server.startServer();
    }
}
