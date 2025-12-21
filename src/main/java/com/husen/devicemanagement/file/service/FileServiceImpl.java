package com.husen.devicemanagement.file.service;

import com.husen.devicemanagement.device.model.Device;
import com.husen.devicemanagement.device.repository.DeviceJpaRepository;
import com.husen.devicemanagement.file.dto.FileResponse;
import com.husen.devicemanagement.file.dto.FileUploadRequest;
import com.husen.devicemanagement.file.dto.FileUploadResponse;
import com.husen.devicemanagement.file.model.FileMetaData;
import com.husen.devicemanagement.file.repository.FileMetaDataRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final DeviceJpaRepository deviceRepository;
    private final FileMetaDataRepository fileRepository;

    public FileServiceImpl(DeviceJpaRepository deviceRepository,
                           FileMetaDataRepository fileRepository) {
        this.deviceRepository = deviceRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public FileUploadResponse generateUploadUrl(Long deviceId,
                                                FileUploadRequest request) {

        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("Device not found"));

        FileMetaData file = new FileMetaData();
        file.setFileName(request.getFileName());
        file.setFileType(request.getFileType());
        file.setSize(request.getSize());

        file.setStorageProvider("MOCK");
        file.setStorageKey(UUID.randomUUID().toString());
        file.setUploadStatus("PENDING");

        file.setCreatedAt(LocalDateTime.now());
        file.setDevice(device);

        fileRepository.save(file);

        return new FileUploadResponse(
                file.getFileId(),
                "https://mock-upload-url/" + file.getStorageKey(),
                900L
        );
    }

    @Override
    public List<FileResponse> getFilesForDevice(Long deviceId) {

        return fileRepository.findByDevice_DeviceId(deviceId)
                .stream()
                .map(file -> new FileResponse(
                        file.getFileName(),
                        file.getSize(),
                        file.getUploadStatus(),
                        file.getFileId()
                ))
                .toList();
    }
}


