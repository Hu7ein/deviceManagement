package com.husen.devicemanagement.file.repository;

import com.husen.devicemanagement.file.model.FileMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileMetaDataRepository extends JpaRepository<FileMetaData, Long> {

    List<FileMetaData> findByDevice_DeviceId(Long deviceId);
}
