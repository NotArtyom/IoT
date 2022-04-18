package com.method.iot.webapp.service.websocket;

import com.method.iot.webapp.model.InterfaceUpdate;

public interface WebsocketService {
    void sendUpdate(InterfaceUpdate update);
}
