package com.husen.devicemanagement.file.controller;


import com.husen.devicemanagement.file.dto.FileUploadCompletedRequest;
import com.husen.devicemanagement.file.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/files")
public class FileEventController {

    private final FileService fileService;

    public FileEventController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload-complete")
    public ResponseEntity<Void> markUploadCompleted(
            @RequestBody FileUploadCompletedRequest request) {

        fileService.markUploadCompleted(
                request.getFileId(),
                request.getSize(),
                request.getBucket(),
                request.getKey()
        );

        return ResponseEntity.ok().build();
    }
}
