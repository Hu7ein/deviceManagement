package com.husen.devicemanagement.file.dto;

import lombok.Data;

@Data
public class FileUploadCompletedRequest {
    private Long fileId;
    private Long size;
    private String bucket;
    private String key;
}
