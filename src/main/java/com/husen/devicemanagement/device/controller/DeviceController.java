package com.husen.devicemanagement.device.controller;

import com.husen.devicemanagement.device.dto.CreateDeviceRequest;
import com.husen.devicemanagement.device.dto.DeviceResponse;
import com.husen.devicemanagement.device.dto.UpdateDeviceRequest;
import com.husen.devicemanagement.device.service.DeviceService;
import com.husen.devicemanagement.util.DeviceGenerator;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PreAuthorize("hasAuthority('DEVICE_CREATE')")
    @PostMapping
    public ResponseEntity<DeviceResponse> createDevice(
            @Valid @RequestBody CreateDeviceRequest request) {
        return ResponseEntity.ok(deviceService.createDevice(request));
    }

    @PreAuthorize("hasAuthority('DEVICE_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getDevice(@PathVariable Long id) {
        return deviceService.getDevice(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('DEVICE_READ')")
    @GetMapping
    public ResponseEntity<List<DeviceResponse>> getAll() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    @PreAuthorize("hasAuthority('DEVICE_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponse> updateDevice(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDeviceRequest request) {

        return ResponseEntity.ok(deviceService.updateDevice(id, request));
    }

    @PreAuthorize("hasAuthority('DEVICE_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('DEVICE_READ')")
    @GetMapping("/search/serial")
    public ResponseEntity<List<DeviceResponse>> searchBySerial(@RequestParam String query) {
        return ResponseEntity.ok(deviceService.searchBySerial(query));
    }

    @PreAuthorize("hasAuthority('DEVICE_READ')")
    @GetMapping("/search/status")
    public ResponseEntity<List<DeviceResponse>> searchByStatus(@RequestParam String status) {
        return ResponseEntity.ok(deviceService.searchByStatus(status));
    }

    @PreAuthorize("hasAuthority('DEVICE_READ')")
    @GetMapping("/search/firmware")
    public ResponseEntity<List<DeviceResponse>> searchByFirmware(@RequestParam String version) {
        return ResponseEntity.ok(deviceService.searchByFirmware(version));
    }

    @PreAuthorize("hasAuthority('DEVICE_READ')")
    @GetMapping("/search/created-date")
    public ResponseEntity<List<DeviceResponse>> searchByCreatedRange(
            @RequestParam String start,
            @RequestParam String end) {

        return ResponseEntity.ok(
                deviceService.searchByCreatedDateRange(
                        LocalDate.parse(start),
                        LocalDate.parse(end)
                )
        );
    }

    @PreAuthorize("hasAuthority('DEVICE_GENERATE')")
    @PostMapping("/generate")
    public ResponseEntity<String> generateDevices(@RequestParam int count) {

        if (count < 1 || count > 1000) {
            return ResponseEntity.badRequest()
                    .body("Count must be between 1 and 1000");
        }

        DeviceGenerator generator = new DeviceGenerator();

        generator.generateTestDevices(count, 1L)
                .values()
                .forEach(d ->
                        deviceService.createDevice(
                                new CreateDeviceRequest() {{
                                    setDeviceStatus(d.getDeviceStatus());
                                    setSerialNumber(d.getSerialNumber());
                                    setFirmwareVersion(d.getFirmwareVersion());
                                }}
                        )
                );

        return ResponseEntity.ok(count + " devices generated successfully.");
    }
}
