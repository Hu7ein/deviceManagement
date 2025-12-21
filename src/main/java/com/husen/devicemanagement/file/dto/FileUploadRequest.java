package com.husen.devicemanagement.file.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FileUploadRequest {
    @NotBlank
    private String fileName;
    @NotBlank
    private String fileType;
}
