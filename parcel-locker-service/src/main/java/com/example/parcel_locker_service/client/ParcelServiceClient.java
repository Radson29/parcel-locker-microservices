package com.example.parcel_locker_service.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@RequiredArgsConstructor
public class ParcelServiceClient {

    private final RestTemplate restTemplate;

    public void updateParcelStatus(Long parcelId, String status) {
        String url = "http://parcel-service:8084/api/parcels/" + parcelId + "/status?status=" + status;
        log.info("Updating parcel status: PUT {}", url);
        restTemplate.put(url, null);
    }
}
