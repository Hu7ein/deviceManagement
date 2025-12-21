package com.husen.devicemanagement.device.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateDeviceRequest {

    @NotBlank(message = "Device status is required")
    @Pattern(
            regexp = "ONLINE|OFFLINE",
            message = "Device status must be ONLINE or OFFLINE"
    )
    private String deviceStatus;

    @NotBlank(message = "Serial number is required")
    private String serialNumber;

    @NotBlank(message = "Firmware version is required")
    private String firmwareVersion;
}
