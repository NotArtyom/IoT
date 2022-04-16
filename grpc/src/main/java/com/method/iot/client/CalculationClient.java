package com.method.iot.client;

import com.method.iot.CalculationServiceGrpc;
import com.method.iot.Messages;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.function.Consumer;

public class CalculationClient {
    private final CalculationServiceGrpc.CalculationServiceStub stub;

    public CalculationClient(String address, int port) {
        stub = CalculationServiceGrpc.newStub(
                ManagedChannelBuilder
                        .forAddress(address, port)
                        .usePlaintext()
                        .build()
        );
    }

    public void requestRoot(double number, Consumer<Double> onReceive) {
        stub.root(
                Messages.RootRequest
                        .newBuilder()
                        .setNumber(number)
                        .build(),
                new StreamObserver<Messages.RootResponse>() {
                    @Override
                    public void onNext(Messages.RootResponse rootResponse) {
                        onReceive.accept(rootResponse.getResult());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                    }

                    @Override
                    public void onCompleted() {
                    }
                }
        );
    }

    public void requestStd(List<Double> values, Consumer<Double> onReceive) {
        StreamObserver<Messages.STDResponse> observer = new StreamObserver<Messages.STDResponse>() {
            @Override
            public void onNext(Messages.STDResponse stdResponse) {
                onReceive.accept(stdResponse.getResult());
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onCompleted() {
            }
        };

        StreamObserver<Messages.STDChunkRequest> stream = stub.std(observer);

        for (Double e : values) {
            stream.onNext(
                    Messages.STDChunkRequest
                            .newBuilder()
                            .setNumber(e)
                            .build()
            );
        }

        stream.onCompleted();
    }

    public void requestDivisors(long target, Consumer<Long> onNext, Runnable onCompleted) {
        stub.dividers(
                Messages.DividersRequest
                        .newBuilder()
                        .setNumber(target)
                        .build(),
                new StreamObserver<Messages.DividerResponse>() {
                    @Override
                    public void onNext(Messages.DividerResponse dividerResponse) {
                        onNext.accept(dividerResponse.getDivider());
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onCompleted() {
                        onCompleted.run();
                    }
                }
        );
    }

    public void requestStreamingMax(List<Double> array, Consumer<Double> onReceive, Runnable onCompleted) {
        StreamObserver<Messages.StreamingMaxResponse> responseObserver = new StreamObserver<Messages.StreamingMaxResponse>() {
            @Override
            public void onNext(Messages.StreamingMaxResponse streamingMaxResponse) {
                onReceive.accept(streamingMaxResponse.getResult());
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                onCompleted.run();
            }
        };

        StreamObserver<Messages.StreamingMaxRequest> requestObserver = stub.streamingMax(responseObserver);

        array.forEach(e -> requestObserver.onNext(
                Messages.StreamingMaxRequest
                        .newBuilder()
                        .setNumber(e)
                        .build()
        ));

        requestObserver.onCompleted();
    }
}
