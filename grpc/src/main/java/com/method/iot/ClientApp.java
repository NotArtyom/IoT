package com.method.iot;

import com.method.iot.client.CalculationClient;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ClientApp {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch finishLatch = new CountDownLatch(4);
        CalculationClient client = new CalculationClient("localhost", 1337);

        // Unary call
        client.requestRoot(
                81,
                (result) -> {
                    System.out.println("Result: " + result);
                    finishLatch.countDown();
                }
        );

        // Client streaming
        client.requestStd(
                Arrays.asList(1d, 2d, 3d, 4d, 5d),
                (result) -> {
                    System.out.println("STD: " + result);
                    finishLatch.countDown();
                }
        );

        // Server streaming
        client.requestDivisors(
                10,
                (divisor) -> System.out.println("Receive divisor: " + divisor),
                finishLatch::countDown
        );

        // Bidirectional streaming
        client.requestStreamingMax(
                Arrays.asList(1d, 2d, 1d, 5d, 4d, 10d, 10d),
                (currentMax) -> System.out.println("Current maximum: " + currentMax),
                finishLatch::countDown
        );

        finishLatch.await(1, TimeUnit.MINUTES);
    }
}
