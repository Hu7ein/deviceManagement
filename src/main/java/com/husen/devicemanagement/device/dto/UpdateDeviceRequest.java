package com.husen.devicemanagement.device.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateDeviceRequest {

    @Pattern(
            regexp = "ONLINE|OFFLINE",
            message = "Device status must be ONLINE or OFFLINE"
    )
    private String deviceStatus;

    private String firmwareVersion;
}
