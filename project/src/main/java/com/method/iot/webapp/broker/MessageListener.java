package com.method.iot.webapp.broker;

import com.method.iot.webapp.service.broker.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageListener implements Runnable {

    @Autowired
    private BrokerService service;

    @Value("${mqtt.broker.pushTopic}")
    private String topic;

    @Override
    public void run() {
        System.out.println("Subscribing" + topic);
        service.subscribeTopic(topic);
    }
}
