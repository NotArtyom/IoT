package com.method.iot.webapp.service.broker;

import com.method.iot.webapp.model.*;
import com.method.iot.webapp.service.storage.StorageService;
import com.method.iot.webapp.service.websocket.WebsocketService;
import com.method.iot.webapp.utils.JsonObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.UUID;

@Service
public class BrokerServiceImpl implements BrokerService, MqttCallback {

    private String brokerAddress = "tcp://broker.mqttdashboard.com:1883";

    private MqttClient client;

    @Autowired
    private JsonObjectMapper objectMapper;

    @Value("${mqtt.broker.serverToClientTopic}")
    private String outboundTopic;

    @Autowired
    private StorageService storageService;

    @Autowired
    private WebsocketService websocketService;

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
    public void startDeviceScanning() {
        sendObject(
                DeviceStateModel
                        .builder()
                        .illumination(Illumination.NONE)
                        .state(State.SCANNING)
                        .build()
        );
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("Connection lost");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String message = new String(mqttMessage.getPayload());

        objectMapper.mapObject(message, ModelUpdateRequest.class).ifPresent(this::handleModelUpdate);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {}

    private void handleModelUpdate(ModelUpdateRequest request) {
        if (storageService.isRegistrationEnabled()) {
            boolean registrationSuccessful = storageService.registerRFID(request);

            sendObject(
                    DeviceStateModel
                            .builder()
                            .state(State.IDLE)
                            .illumination(registrationSuccessful ? Illumination.GREEN : Illumination.RED)
                            .build()
            );

            websocketService.sendUpdate(
                    InterfaceUpdate
                            .builder()
                            .action(Action.REGISTER)
                            .success(registrationSuccessful)
                            .ids(storageService.getRegisteredIds())
                            .build()
            );

        } else {
            boolean checkSuccessful = storageService.checkRFID(request);

            sendObject(
                    DeviceStateModel
                            .builder()
                            .state(State.IDLE)
                            .illumination(checkSuccessful ? Illumination.GREEN : Illumination.RED)
                            .build()
            );

            websocketService.sendUpdate(
                    InterfaceUpdate
                            .builder()
                            .action(Action.CHECK)
                            .success(checkSuccessful)
                            .ids(Collections.singletonList(request.getUuid()))
                            .build()
            );
        }
    }

    private void sendObject(Object o) {
        objectMapper.toJson(o)
                .ifPresent(json -> {
                    MqttMessage message = new MqttMessage();
                    message.setPayload(json.getBytes(StandardCharsets.UTF_8));
                    message.setQos(2);
                    try {
                        client.publish(outboundTopic, message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
