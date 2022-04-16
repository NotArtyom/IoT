package com.method.iot.kafkagrpc.web.controller;

import com.method.iot.kafkagrpc.web.model.MessageDTO;
import com.method.iot.kafkagrpc.web.model.StatusDTO;
import com.method.iot.kafkagrpc.web.service.MessageProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    private MessageProcessingService service;

    @PostMapping(value = "/sendMessage")
    public ResponseEntity<?> processMessage(@RequestBody MessageDTO message) {
        StatusDTO status = service.processMessage(message);
        return ResponseEntity
                .ok()
                .body(status);
    }
}
