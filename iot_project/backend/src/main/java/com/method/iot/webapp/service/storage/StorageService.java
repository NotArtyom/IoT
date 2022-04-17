package com.method.iot.webapp.service.storage;

import com.method.iot.webapp.model.ModelUpdateRequest;

import java.util.List;

public interface StorageService {
    List<String> getRegisteredIds();
    boolean registerRFID(ModelUpdateRequest request);
    boolean checkRFID(ModelUpdateRequest request);
    boolean isRegistrationEnabled();
    void setRegistrationEnabled(boolean enabled);
}
