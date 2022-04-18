package com.method.iot.webapp.service.websocket;

import com.method.iot.webapp.model.InterfaceUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebsocketServiceImpl implements WebsocketService {

    private SimpMessagingTemplate template;

    @Lazy
    @Autowired
    public WebsocketServiceImpl(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Override
    public void sendUpdate(InterfaceUpdate update) {
        template.convertAndSend("/topic/updates", update);
    }
}
