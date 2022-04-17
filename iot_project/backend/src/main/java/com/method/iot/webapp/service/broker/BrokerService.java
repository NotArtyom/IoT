package com.method.iot.webapp.service.broker;

import com.method.iot.webapp.model.DeviceStateModel;

public interface BrokerService {
    void subscribeTopic(String topic);
    void startDeviceScanning();
}
