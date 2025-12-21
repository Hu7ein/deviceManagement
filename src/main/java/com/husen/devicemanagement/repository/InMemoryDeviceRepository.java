package com.husen.devicemanagement.repository;

import com.husen.devicemanagement.device.model.Device;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryDeviceRepository  {

    private final Map<Long, Device> store = new HashMap<>();
    

    public Device save(Device device) {
        store.put(device.getDeviceId(), device);
        return device;
    }


    public Optional<Device> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }


    public List<Device> findAll() {
        return new ArrayList<>(store.values());
    }


    public void deleteById(Long id) {
        store.remove(id);
    }
}
