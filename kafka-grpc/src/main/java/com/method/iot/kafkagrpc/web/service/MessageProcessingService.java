package com.method.iot.kafkagrpc.web.service;

import com.method.iot.kafkagrpc.web.model.MessageDTO;
import com.method.iot.kafkagrpc.web.model.StatusDTO;

public interface MessageProcessingService {
    StatusDTO processMessage(MessageDTO message);
}
