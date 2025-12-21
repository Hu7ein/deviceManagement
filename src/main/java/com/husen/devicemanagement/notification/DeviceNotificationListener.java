package com.husen.devicemanagement.notification;

import com.husen.devicemanagement.common.event.DeviceCreatedEvent;
import com.husen.devicemanagement.common.event.DeviceDeletedEvent;
import com.husen.devicemanagement.common.event.DeviceUpdatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class DeviceNotificationListener {

    @Async
    @EventListener
    public void onDeviceCreated(DeviceCreatedEvent event) {

        String message =
                "[" + LocalDateTime.now() + "] INFO: Device created | id="
                        + event.getDeviceId()
                        + ", serial=" + event.getSerialNumber();

        System.out.println(message);
    }

    @Async
    @EventListener
    public void onDeviceUpdated(DeviceUpdatedEvent event) {

        if ("OFFLINE".equalsIgnoreCase(event.getDeviceStatus())) {

            String alert =
                    "[" + LocalDateTime.now() + "] ALERT: Device OFFLINE | id="
                            + event.getDeviceId();

            System.out.println(alert);
        }
    }

    @Async
    @EventListener
    public void onDeviceDeleted(DeviceDeletedEvent event) {

        String warning =
                "[" + LocalDateTime.now() + "] WARNING: Device deleted | id="
                        + event.getDeviceId();

        System.out.println(warning);
    }

}
