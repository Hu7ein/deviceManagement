package com.husen.devicemanagement.file.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FileResponse {
    private Long fileId;
    private String fileName;
    private Long size;
    private String status;

    public FileResponse(String fileName, Long size, String status, Long fileId) {
        this.fileName = fileName;
        this.size = size;
        this.status = status;
        this.fileId = fileId;
    }
}
