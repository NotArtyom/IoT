package com.method.iot.kafkagrpc.web.service;

import com.method.iot.kafkagrpc.MessageServiceGrpc;
import com.method.iot.kafkagrpc.Messages;
import com.method.iot.kafkagrpc.web.model.MessageDTO;
import com.method.iot.kafkagrpc.web.model.StatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProcessingServiceImpl implements MessageProcessingService {

    @Autowired
    private MessageServiceGrpc.MessageServiceBlockingStub grpcService;

    @Override
    public StatusDTO processMessage(MessageDTO message) {
        Messages.MessageResponse response = grpcService.sendMessage(
                Messages.MessageRequest
                        .newBuilder()
                        .setPayload(message.getPayload())
                        .build()
        );

        return new StatusDTO(response.getStatus());
    }
}
