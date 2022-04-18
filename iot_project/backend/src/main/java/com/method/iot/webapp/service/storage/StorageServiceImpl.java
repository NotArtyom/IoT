package com.method.iot.webapp.service.storage;

import com.method.iot.webapp.model.ModelUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageServiceImpl implements StorageService {

    private List<String> savedIds = new ArrayList<>();
    private boolean isRegistrationMode = false;

    @Override
    public List<String> getRegisteredIds() {
        return savedIds;
    }

    @Override
    public boolean registerRFID(ModelUpdateRequest request) {
        if (!savedIds.contains(request.getUuid())) {
            savedIds.add(request.getUuid());
            return true;
        }

        return false;
    }

    @Override
    public boolean checkRFID(ModelUpdateRequest request) {
        return savedIds.contains(request.getUuid());
    }

    @Override
    public boolean isRegistrationEnabled() {
        return isRegistrationMode;
    }

    @Override
    public void setRegistrationEnabled(boolean enabled) {
        isRegistrationMode = enabled;
    }
}
