package com.husen.devicemanagement.file.model;

import com.husen.devicemanagement.device.model.Device;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "device_files")
@Data
public class FileMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    private String fileName;
    private String fileType;
    private Long size;

    // Cloud-related fields
    private String storageProvider;   //S3
    private String storageKey;         // s3 object key
    private String uploadStatus;       // PENDING / COMPLETED / FAILED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;
}
