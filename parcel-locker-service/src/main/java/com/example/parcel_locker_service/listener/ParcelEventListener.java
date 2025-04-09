package com.example.parcel_locker_service.listener;

import com.example.parcel_locker_service.config.RabbitMQConfig;
import com.example.parcel_locker_service.entity.Locker;
import com.example.parcel_locker_service.entity.LockerSlot;
import com.example.parcel_locker_service.entity.LockerSlotStatus;
import com.example.parcel_locker_service.event.ParcelCreatedEvent;
import com.example.parcel_locker_service.repository.LockerRepository;
import com.example.parcel_locker_service.repository.LockerSlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ParcelEventListener {

    private final LockerRepository lockerRepository;
    private final LockerSlotRepository lockerSlotRepository;

    @RabbitListener(queues = RabbitMQConfig.PARCEL_CREATED_QUEUE)
    public void handleParcelCreatedEvent(ParcelCreatedEvent event) {
        log.info("Received ParcelCreatedEvent: {}", event);

        Locker locker = lockerRepository.findByIdWithSlots(Long.valueOf(event.getLockerId()))
                .orElseThrow(() -> new RuntimeException("Locker not found"));


        LockerSlot slot = locker.getSlots().stream()
                .filter(s -> s.getSize().name().equals(event.getParcelSize()))
                .filter(s -> s.getStatus() == LockerSlotStatus.FREE)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No free slot available for size " + event.getParcelSize()));

        slot.setStatus(LockerSlotStatus.OCCUPIED);
        lockerSlotRepository.save(slot);

        log.info("Slot number {} in locker {} has been occupied for parcel {}", slot.getSlotNumber(), locker.getId(), event.getParcelId());
    }
}
