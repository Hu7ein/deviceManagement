package com.husen.devicemanagement.device.service;

import com.husen.devicemanagement.device.dto.CreateDeviceRequest;
import com.husen.devicemanagement.device.dto.DeviceResponse;
import com.husen.devicemanagement.device.dto.UpdateDeviceRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DeviceService {

    DeviceResponse createDevice(CreateDeviceRequest request);

    Optional<DeviceResponse> getDevice(Long id);

    List<DeviceResponse> getAllDevices();

    DeviceResponse updateDevice(Long id, UpdateDeviceRequest request);

    void deleteDevice(Long id);

    List<DeviceResponse> searchBySerial(String serial);

    List<DeviceResponse> searchByStatus(String status);

    List<DeviceResponse> searchByFirmware(String firmware);

    List<DeviceResponse> searchByCreatedDateRange(LocalDate start, LocalDate end);
}
