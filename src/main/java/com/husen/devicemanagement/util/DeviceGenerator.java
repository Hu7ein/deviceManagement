package com.husen.devicemanagement.util;

import com.husen.devicemanagement.device.model.Device;
import com.husen.devicemanagement.device.model.DeviceStatus;
import com.husen.devicemanagement.file.model.FileMetaData;

import java.time.LocalDateTime;
import java.util.*;

public class DeviceGenerator {

    private static final String[] FW_VERSIONS = {
            "1.0.0",
            "1.1.0",
            "2.0.0",
            "2.1.0"
    };

    private final Set<String> usedSerials = new HashSet<>();
    private final Random random = new Random();

    public Map<Long, Device> generateTestDevices(int numberOfDevices, long startId) {

        Map<Long, Device> deviceMap = new HashMap<>();

        for (long i = 0; i < numberOfDevices; i++) {

            long deviceId = startId + i;

            Device device = new Device();
            //device.setDeviceId(deviceId);
            device.setDeviceStatus(randomStatus().toString());
            device.setCreatedDate(randomPastDate());
            device.setFirmwareVersion(randomFirmware());
            device.setSerialNumber(generateSerialNumber());
            device.setModifiedDate(randomModifiedDate(device.getCreatedDate()));

            deviceMap.put(deviceId, device);
        }

        return deviceMap;
    }

    private DeviceStatus randomStatus() {
        return random.nextBoolean() ? DeviceStatus.ONLINE : DeviceStatus.OFFLINE;
    }

    private String randomFirmware() {
        return FW_VERSIONS[random.nextInt(FW_VERSIONS.length)];
    }

    private String generateSerialNumber() {
        String serial;
        do {
            serial = "HN-" + (100000 + random.nextInt(900000));
        } while (!usedSerials.add(serial));
        return serial;
    }

    private LocalDateTime randomPastDate() {
        return LocalDateTime.now()
                .minusDays(random.nextInt(30))
                .minusHours(random.nextInt(24));
    }

    private LocalDateTime randomModifiedDate(LocalDateTime createdDate) {
        return createdDate.plusHours(random.nextInt(72));
    }

}
