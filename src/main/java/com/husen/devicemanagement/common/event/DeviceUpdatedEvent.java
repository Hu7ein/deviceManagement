package com.husen.devicemanagement.common.event;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class DeviceUpdatedEvent {

    private final Long deviceId;
    private final String firmwareVersion;
    private final String deviceStatus;
    private final LocalDateTime modifiedDate;

    public DeviceUpdatedEvent(
            Long deviceId,
            String firmwareVersion,
            String deviceStatus,
            LocalDateTime modifiedDate
    ) {
        this.deviceId = deviceId;
        this.firmwareVersion = firmwareVersion;
        this.deviceStatus = deviceStatus;
        this.modifiedDate = modifiedDate;
    }
}
