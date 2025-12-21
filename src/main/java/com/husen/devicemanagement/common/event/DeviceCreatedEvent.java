package com.husen.devicemanagement.common.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeviceCreatedEvent {

    private final Long deviceId;
    private final String serialNumber;
    private final String firmwareVersion;
    private final String deviceStatus;
    private final LocalDateTime createdDate;

    public DeviceCreatedEvent(
            Long deviceId,
            String serialNumber,
            String firmwareVersion,
            String deviceStatus,
            LocalDateTime createdDate
    ) {
        this.deviceId = deviceId;
        this.serialNumber = serialNumber;
        this.firmwareVersion = firmwareVersion;
        this.deviceStatus = deviceStatus;
        this.createdDate = createdDate;
    }
}
