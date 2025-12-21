package com.husen.devicemanagement.file.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileUploadResponse {
    private Long fileId;
    private String uploadUrl;
    private Long expiresIn;
}
