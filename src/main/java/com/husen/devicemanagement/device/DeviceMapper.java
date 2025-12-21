package com.husen.devicemanagement.device.mapper;

import com.husen.devicemanagement.device.dto.CreateDeviceRequest;
import com.husen.devicemanagement.device.dto.DeviceResponse;
import com.husen.devicemanagement.device.model.Device;

public class DeviceMapper {

    public static Device toEntity(CreateDeviceRequest request) {
        Device device = new Device();
        device.setDeviceStatus(request.getDeviceStatus());
        device.setSerialNumber(request.getSerialNumber());
        device.setFirmwareVersion(request.getFirmwareVersion());
        return device;
    }

    public static DeviceResponse toResponse(Device device) {
        DeviceResponse response = new DeviceResponse();
        response.setDeviceId(device.getDeviceId());
        response.setDeviceStatus(device.getDeviceStatus());
        response.setSerialNumber(device.getSerialNumber());
        response.setFirmwareVersion(device.getFirmwareVersion());
        response.setCreatedDate(device.getCreatedDate());
        response.setModifiedDate(device.getModifiedDate());
        return response;
    }
}
