package com.method.iot.kafkagrpc.grpc.service;

import com.method.iot.kafkagrpc.MessageServiceGrpc;
import com.method.iot.kafkagrpc.Messages;
import io.grpc.stub.StreamObserver;

public class MessageService extends MessageServiceGrpc.MessageServiceImplBase {

    private KafkaService service = new KafkaService();

    @Override
    public void sendMessage(Messages.MessageRequest request, StreamObserver<Messages.MessageResponse> responseObserver) {
        service.sendMessage(request.getPayload());
        System.out.println("Sending message with text: " + request.getPayload());
        responseObserver.onNext(
                Messages.MessageResponse
                        .newBuilder()
                        .setStatus("success")
                        .build()
        );
        responseObserver.onCompleted();
    }
}
