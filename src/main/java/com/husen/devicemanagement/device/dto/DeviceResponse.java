package com.husen.devicemanagement.device.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeviceResponse {

    private Long deviceId;
    private String deviceStatus;
    private String serialNumber;
    private String firmwareVersion;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
