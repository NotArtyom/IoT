package com.method.iot.webapp.service.storage;

public interface StorageService {
    void saveRFID(String rfid);
    boolean hasRFID(String rfid);
}
