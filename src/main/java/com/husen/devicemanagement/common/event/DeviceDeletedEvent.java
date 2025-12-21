package com.husen.devicemanagement.common.event;

import lombok.Data;

@Data
public class DeviceDeletedEvent {

    private final Long deviceId;

    public DeviceDeletedEvent(Long deviceId) {
        this.deviceId = deviceId;
    }
}
