package com.method.iot.service;

import com.method.iot.CalculationServiceGrpc;
import com.method.iot.Messages;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class CalculationServiceImpl extends CalculationServiceGrpc.CalculationServiceImplBase {
    @Override
    public void root(Messages.RootRequest request, StreamObserver<Messages.RootResponse> responseObserver) {
        double num = request.getNumber();
        responseObserver.onNext(
                Messages.RootResponse.newBuilder()
                        .setResult(Math.sqrt(num))
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<Messages.STDChunkRequest> std(StreamObserver<Messages.STDResponse> responseObserver) {
        return new StreamObserver<Messages.STDChunkRequest>() {
            private final List<Double> storage = new ArrayList<>();

            @Override
            public void onNext(Messages.STDChunkRequest stdChunkRequest) {
                storage.add(stdChunkRequest.getNumber());
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                double average = storage.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0);
                double std = Math.sqrt(
                        storage.stream()
                                .map(e -> Math.pow(e - average, 2))
                                .mapToDouble(Double::doubleValue)
                                .sum() / (double) storage.size()
                );

                responseObserver.onNext(
                        Messages.STDResponse
                                .newBuilder()
                                .setResult(std)
                                .build()
                );

                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void dividers(Messages.DividersRequest request, StreamObserver<Messages.DividerResponse> responseObserver) {
        long target = request.getNumber();

        while (target % 2 == 0) {
            sendDividerResponse(responseObserver, 2);
            target /= 2;
        }

        for (long i = 3; i <= Math.sqrt(target); i += 2) {
            while (target % i == 0) {
                sendDividerResponse(responseObserver, i);
                target /= i;
            }
        }

        if (target > 2)
            sendDividerResponse(responseObserver, target);

        responseObserver.onCompleted();
    }

    private void sendDividerResponse(StreamObserver<Messages.DividerResponse> responseObserver, long divider) {
        responseObserver.onNext(
                Messages.DividerResponse
                        .newBuilder()
                        .setDivider(divider)
                        .build()
        );
    }

    @Override
    public StreamObserver<Messages.StreamingMaxRequest> streamingMax(StreamObserver<Messages.StreamingMaxResponse> responseObserver) {
        return new StreamObserver<Messages.StreamingMaxRequest>() {
            private Double currentMax = -1d;

            @Override
            public void onNext(Messages.StreamingMaxRequest streamingMaxRequest) {
                if (streamingMaxRequest.getNumber() > currentMax) {
                    currentMax = streamingMaxRequest.getNumber();
                }

                responseObserver.onNext(
                        Messages.StreamingMaxResponse
                                .newBuilder()
                                .setResult(currentMax)
                                .build()
                );
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
