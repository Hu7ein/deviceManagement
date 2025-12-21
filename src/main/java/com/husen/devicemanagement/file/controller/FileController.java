package com.husen.devicemanagement.file.controller;

import com.husen.devicemanagement.file.dto.FileResponse;
import com.husen.devicemanagement.file.dto.FileUploadRequest;
import com.husen.devicemanagement.file.dto.FileUploadResponse;
import com.husen.devicemanagement.file.service.FileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices/{deviceId}/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PreAuthorize("hasAuthority('DEVICE_UPDATE')")
    @PostMapping("/upload-url")
    public ResponseEntity<FileUploadResponse> generateUploadUrl(
            @PathVariable Long deviceId,
            @Valid @RequestBody FileUploadRequest request) {

        return ResponseEntity.ok(
                fileService.generateUploadUrl(deviceId, request)
        );
    }


    @PreAuthorize("hasAuthority('DEVICE_READ')")
    @GetMapping
    public ResponseEntity<List<FileResponse>> listFiles(@PathVariable Long deviceId) {

        return ResponseEntity.ok(
                fileService.getFilesForDevice(deviceId)
        );
    }
}
