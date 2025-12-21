package com.husen.devicemanagement.util;

import com.husen.devicemanagement.file.model.FileMetaData;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileMetadataReader {

    public static FileMetaData read(String path) throws IOException {

        Path filePath = Paths.get(path);

        if (!Files.exists(filePath)) {
            throw new IOException("File does not exist");
        }

        BasicFileAttributes attr =
                Files.readAttributes(filePath, BasicFileAttributes.class);

        FileMetaData details = new FileMetaData();
        details.setFileName(filePath.getFileName().toString());
        details.setSize(attr.size());
        details.setFileType(Files.isDirectory(filePath) ? "DIRECTORY" : "FILE");

        return details;
    }
}
