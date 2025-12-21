package com.husen.devicemanagement.device.service;

import com.husen.devicemanagement.common.event.DeviceCreatedEvent;
import com.husen.devicemanagement.common.event.DeviceDeletedEvent;
import com.husen.devicemanagement.common.event.DeviceUpdatedEvent;
import com.husen.devicemanagement.device.dto.CreateDeviceRequest;
import com.husen.devicemanagement.device.dto.DeviceResponse;
import com.husen.devicemanagement.device.dto.UpdateDeviceRequest;
import com.husen.devicemanagement.device.mapper.DeviceMapper;
import com.husen.devicemanagement.device.model.Device;
import com.husen.devicemanagement.device.repository.DeviceJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceJpaRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public DeviceServiceImpl(
            @Qualifier("deviceJpaRepository") DeviceJpaRepository repository,
            ApplicationEventPublisher eventPublisher
    ) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public DeviceResponse createDevice(CreateDeviceRequest request) {

        Device device = DeviceMapper.toEntity(request);

        LocalDateTime now = LocalDateTime.now();
        device.setCreatedDate(now);
        device.setModifiedDate(now);

        Device saved = repository.save(device);

        eventPublisher.publishEvent(
                new DeviceCreatedEvent(
                        saved.getDeviceId(),
                        saved.getSerialNumber(),
                        saved.getFirmwareVersion(),
                        saved.getDeviceStatus(),
                        saved.getCreatedDate()
                )
        );

        return DeviceMapper.toResponse(saved);
    }

    @Override
    public Optional<DeviceResponse> getDevice(Long id) {
        return repository.findById(id).map(DeviceMapper::toResponse);
    }

    @Override
    public List<DeviceResponse> getAllDevices() {
        return repository.findAll()
                .stream()
                .map(DeviceMapper::toResponse)
                .toList();
    }

    @Override
    public DeviceResponse updateDevice(Long id, UpdateDeviceRequest request) {

        Device device = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Device not found"));

        if (request.getDeviceStatus() != null) {
            device.setDeviceStatus(request.getDeviceStatus());
        }
        if (request.getFirmwareVersion() != null) {
            device.setFirmwareVersion(request.getFirmwareVersion());
        }

        device.setModifiedDate(LocalDateTime.now());

        Device saved = repository.save(device);

        eventPublisher.publishEvent(
                new DeviceUpdatedEvent(
                        saved.getDeviceId(),
                        saved.getFirmwareVersion(),
                        saved.getDeviceStatus(),
                        saved.getModifiedDate()
                )
        );

        return DeviceMapper.toResponse(saved);
    }

    @Override
    public void deleteDevice(Long id) {

        Device device = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Device not found"));

        repository.deleteById(id);

        eventPublisher.publishEvent(new DeviceDeletedEvent(id));
    }

    // ---------------- SEARCH METHODS ----------------

    @Override
    public List<DeviceResponse> searchBySerial(String serial) {
        String query = serial.toLowerCase();
        return repository.findAll().stream()
                .filter(d ->
                        d.getSerialNumber() != null &&
                                d.getSerialNumber().toLowerCase().contains(query)
                )
                .map(DeviceMapper::toResponse)
                .toList();
    }

    @Override
    public List<DeviceResponse> searchByStatus(String status) {
        return repository.findAll().stream()
                .filter(d ->
                        d.getDeviceStatus() != null &&
                                d.getDeviceStatus().equalsIgnoreCase(status)
                )
                .map(DeviceMapper::toResponse)
                .toList();
    }

    @Override
    public List<DeviceResponse> searchByFirmware(String firmware) {
        return repository.findAll().stream()
                .filter(d ->
                        d.getFirmwareVersion() != null &&
                                firmware.equalsIgnoreCase(d.getFirmwareVersion())
                )
                .map(DeviceMapper::toResponse)
                .toList();
    }

    @Override
    public List<DeviceResponse> searchByCreatedDateRange(LocalDate start, LocalDate end) {

        LocalDateTime startDt = start.atStartOfDay();
        LocalDateTime endDt = end.atTime(23, 59, 59);

        return repository.findAll().stream()
                .filter(d ->
                        d.getCreatedDate() != null &&
                                !d.getCreatedDate().isBefore(startDt) &&
                                !d.getCreatedDate().isAfter(endDt)
                )
                .map(DeviceMapper::toResponse)
                .toList();
    }
}
