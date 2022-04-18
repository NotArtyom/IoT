package com.method.iot.webapp.controller;

import com.method.iot.webapp.model.ActionRequest;
import com.method.iot.webapp.model.DeviceStateModel;
import com.method.iot.webapp.service.broker.BrokerService;
import com.method.iot.webapp.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/")
public class RFIDRegistrationController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private BrokerService brokerService;

    @MessageMapping("/action")
    public void changeRegistrationMode(ActionRequest request) {
        switch (request.getAction()) {
            case CHECK:
                storageService.setRegistrationEnabled(false);
                break;
            case REGISTER:
                storageService.setRegistrationEnabled(true);
                break;
        }

        brokerService.startDeviceScanning();
    }

    @GetMapping("/test")
    public String testPage() {
        return "index";
    }
}
