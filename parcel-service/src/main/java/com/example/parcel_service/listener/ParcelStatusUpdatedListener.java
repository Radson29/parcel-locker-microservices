package com.example.parcel_service.listener;

import com.example.parcel_service.service.ParcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParcelStatusUpdatedListener {

    private final ParcelService parcelService;

    @RabbitListener(queues = "parcel.status.updated")
    public void handleParcelStatusUpdated(Map<String, Object> message) {
        try {
            log.info("Received parcel status update: {}", message);

            Long parcelId = ((Number) message.get("parcelId")).longValue();
            String status = (String) message.get("status");

            parcelService.updateParcelStatus(parcelId, status);
            log.info("Successfully updated parcel {} to status {}", parcelId, status);
        } catch (Exception e) {
            log.error("Error processing parcel status update", e);
        }
    }
}
