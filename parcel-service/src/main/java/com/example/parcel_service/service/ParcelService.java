package com.example.parcel_service.service;

import com.example.parcel_service.dto.ParcelRequest;
import com.example.parcel_service.entity.Parcel;

import com.example.parcel_service.entity.ParcelStatus;
import com.example.parcel_service.event.ParcelCreatedEvent;
import com.example.parcel_service.publisher.ParcelEventPublisher;
import com.example.parcel_service.repository.ParcelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParcelService {

    private final ParcelRepository parcelRepository;
    private final ParcelEventPublisher eventPublisher;


    public Parcel createParcel(ParcelRequest request) {
        Parcel parcel = mapToParcel(request);
        Parcel savedParcel = parcelRepository.save(parcel);
        eventPublisher.publishParcelCreatedEvent(savedParcel);
        return savedParcel;
    }

    public List<Parcel> getAllParcels() {
        return parcelRepository.findAll();
    }

    public Parcel getParcelById(Long id) {
        return parcelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parcel not found with id: " + id));
    }

    public Parcel updateParcelStatus(Long id, String status) {
        Parcel parcel = getParcelById(id);
        log.info("📦 Updating parcel {} to status: {}", id, status);
        parcel.setStatus(ParcelStatus.valueOf(status.toUpperCase()));
        return parcelRepository.save(parcel);
    }

    public void deleteParcel(Long id) {
        if (!parcelRepository.existsById(id)) {
            throw new RuntimeException("Parcel not found with id: " + id);
        }
        parcelRepository.deleteById(id);
    }

    private Parcel mapToParcel(ParcelRequest request) {
        return Parcel.builder()
                .lockerId(request.getLockerId())
                .userId(request.getUserId())
                .recipientName(request.getRecipientName())
                .recipientAddress(request.getRecipientAddress())
                .status(ParcelStatus.CREATED)
                .size(request.getSize())
                .build();
    }
}