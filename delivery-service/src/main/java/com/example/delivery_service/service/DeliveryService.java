package com.example.delivery_service.service;

import com.example.delivery_service.entity.Delivery;
import com.example.delivery_service.event.DeliveryCompletedEvent;
import com.example.delivery_service.event.DeliveryStartedEvent;
import com.example.delivery_service.event.ParcelStatusUpdatedEvent;
import com.example.delivery_service.publisher.DeliveryEventPublisher;
import com.example.delivery_service.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryEventPublisher eventPublisher;

    public Delivery createDelivery(Delivery delivery) {
        delivery.setStatus("STARTED");
        Delivery savedDelivery = deliveryRepository.save(delivery);

        DeliveryStartedEvent event = new DeliveryStartedEvent(
                savedDelivery.getId(),
                savedDelivery.getCourierId(),
                savedDelivery.getParcelId(),
                savedDelivery.getStatus()
        );

        eventPublisher.publishDeliveryStartedEvent(event);

        return savedDelivery;
    }

    public Delivery getDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));
    }

    public Delivery completeDelivery(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));

        delivery.setStatus("COMPLETED");
        deliveryRepository.save(delivery);

        Delivery freshDelivery = deliveryRepository.findById(delivery.getId())
                .orElseThrow(() -> new RuntimeException("Delivery not found after update"));

        log.info(" Delivery {} updated to status: {}", freshDelivery.getId(), freshDelivery.getStatus());

        DeliveryCompletedEvent deliveryEvent = new DeliveryCompletedEvent(
                freshDelivery.getId(),
                freshDelivery.getCourierId(),
                freshDelivery.getParcelId(),
                freshDelivery.getStatus()
        );
        eventPublisher.publishDeliveryCompletedEvent(deliveryEvent);


        eventPublisher.publishParcelStatusUpdatedEvent(
                freshDelivery.getParcelId(),
                "DELIVERED_TO_LOCKER"
        );
        log.info(" Published ParcelStatusUpdatedEvent for parcel {} with status {}",
                freshDelivery.getParcelId(), "DELIVERED_TO_LOCKER");

        return freshDelivery;
    }
}