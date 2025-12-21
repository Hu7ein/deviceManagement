package com.husen.devicemanagement.device.repository;

import com.husen.devicemanagement.device.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceJpaRepository extends JpaRepository<Device, Long>{
    List<Device> findBySerialNumberContainingIgnoreCase(String serial);
    List<Device> findByDeviceStatusIgnoreCase(String status);
    List<Device> findByFirmwareVersion(String fw);
}
