package com.husen.devicemanagement.file.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.husen.devicemanagement.device.model.Device;
import com.husen.devicemanagement.device.repository.DeviceJpaRepository;
import com.husen.devicemanagement.file.dto.FileResponse;
import com.husen.devicemanagement.file.dto.FileUploadRequest;
import com.husen.devicemanagement.file.dto.FileUploadResponse;
import com.husen.devicemanagement.file.model.FileMetaData;
import com.husen.devicemanagement.file.repository.FileMetaDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private final DeviceJpaRepository deviceRepository;
    private final FileMetaDataRepository fileRepository;
    private final AmazonS3 s3Client;

    @Value("${app.s3.bucket}")
    private String bucketName;

    public FileServiceImpl(DeviceJpaRepository deviceRepository,
                           FileMetaDataRepository fileRepository,
                           AmazonS3 s3Client) {
        this.deviceRepository = deviceRepository;
        this.fileRepository = fileRepository;
        this.s3Client = s3Client;
    }

    @Override
    public FileUploadResponse generateUploadUrl(Long deviceId,
                                                FileUploadRequest request) {

        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("Device not found"));

        FileMetaData file = new FileMetaData();
        file.setFileName(request.getFileName());
        file.setFileType(request.getFileType());
        file.setUploadStatus("PENDING");
        file.setStorageProvider("S3");
        file.setUploadedAt(LocalDateTime.now());
        file.setDevice(device);

        fileRepository.save(file);

        String s3Key = String.format(
                "device/%d/file/%d/%s",
                deviceId,
                file.getFileId(),
                request.getFileName()
        );

        file.setStorageKey(s3Key);
        fileRepository.save(file);

        Date expiry = Date.from(Instant.now().plusSeconds(900));

        GeneratePresignedUrlRequest presignedRequest =
                new GeneratePresignedUrlRequest(bucketName, s3Key)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(expiry);

        URL uploadUrl = s3Client.generatePresignedUrl(presignedRequest);

        System.out.println("PRESIGNED_URL = " + uploadUrl);


        return new FileUploadResponse(
                file.getFileId(),
                uploadUrl.toString(),
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

    @Override
    public void markUploadCompleted(Long fileId, Long size, String bucket, String key) {

        FileMetaData file = fileRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("File not found"));

        file.setUploadStatus("COMPLETED");
        file.setSize(size);
        file.setStorageProvider("S3");
        file.setStorageKey(key);
        file.setUploadedAt(LocalDateTime.now());
        file.setUpdatedAt(LocalDateTime.now());

        fileRepository.save(file);
    }

}



