package com.husen.devicemanagement.file.dto;

import lombok.Data;

@Data
public class FileUploadRequest {
    private String fileName;
    private String fileType;
    private Long size;
}
