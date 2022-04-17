package com.method.iot.webapp.service.broker;

import com.method.iot.webapp.service.storage.StorageService;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BrokerServiceImpl implements BrokerService, MqttCallback {

    private String brokerAddress = "tcp://broker.mqttdashboard.com:1883";

    private MqttClient client;

    public BrokerServiceImpl() {
        try {
            System.out.println("Connecting client");
            client = new MqttClient(brokerAddress, UUID.randomUUID().toString(), new MemoryPersistence());
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);
            client.connect(connectOptions);
            client.setCallback(this);
            System.out.println("Connected");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribeTopic(String topic) {
        try {
            client.subscribe(topic, 2);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("Connection list");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println("Log_receive_message: " + new String(mqttMessage.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
