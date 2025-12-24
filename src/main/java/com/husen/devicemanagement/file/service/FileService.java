package com.husen.devicemanagement.file.service;

import com.husen.devicemanagement.file.dto.FileUploadRequest;
import com.husen.devicemanagement.file.dto.FileUploadResponse;
import com.husen.devicemanagement.file.dto.FileResponse;

import java.util.List;

public interface FileService {

    FileUploadResponse generateUploadUrl(Long deviceId, FileUploadRequest request);

    List<FileResponse> getFilesForDevice(Long deviceId);

    void markUploadCompleted(Long fileId, Long size, String bucket, String key);
}
