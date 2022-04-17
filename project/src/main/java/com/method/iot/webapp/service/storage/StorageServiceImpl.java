package com.method.iot.webapp.service.storage;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StorageServiceImpl implements StorageService {

    private Set<String> savedIds = new HashSet<>();

    @Override
    public void saveRFID(String rfid) {
        savedIds.add(rfid);
    }

    @Override
    public boolean hasRFID(String rfid) {
        return savedIds.contains(rfid);
    }
}
