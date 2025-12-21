package com.husen.devicemanagement.device.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "devices")
@Data
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceId;

    private String deviceStatus;
    private String serialNumber;
    private String firmwareVersion;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}
