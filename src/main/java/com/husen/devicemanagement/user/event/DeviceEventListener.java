package com.husen.devicemanagement.user.event;

import com.husen.devicemanagement.common.event.DeviceCreatedEvent;
import com.husen.devicemanagement.common.event.DeviceDeletedEvent;
import com.husen.devicemanagement.common.event.DeviceUpdatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DeviceEventListener {

    @Async
    @EventListener
    public void handleDeviceCreated(DeviceCreatedEvent event) {
        System.out.println(
                "[EVENT] Device Created → id=" + event.getDeviceId()
                        + ", serial=" + event.getSerialNumber()
                        + ", firmware=" + event.getFirmwareVersion()
        );
    }

    @Async
    @EventListener
    public void handleDeviceUpdated(DeviceUpdatedEvent event) {
        System.out.println(
                "[EVENT] Device Updated → id=" + event.getDeviceId()
                        + ", status=" + event.getDeviceStatus()
                        + ", modified=" + event.getModifiedDate()
        );
    }

    @Async
    @EventListener
    public void handleDeviceDeleted(DeviceDeletedEvent event) {
        System.out.println(
                "[EVENT] Device Deleted → id=" + event.getDeviceId()
        );
    }
}


