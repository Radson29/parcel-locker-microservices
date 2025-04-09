package com.example.parcel_service.controller;


import com.example.parcel_service.dto.ParcelRequest;
import com.example.parcel_service.dto.ParcelStatusUpdateRequest;
import com.example.parcel_service.entity.Parcel;
import com.example.parcel_service.service.ParcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parcels")
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelService parcelService;

    @PostMapping
    public Parcel createParcel(@RequestBody ParcelRequest request) {
        return parcelService.createParcel(request);
    }

    @GetMapping
    public List<Parcel> getAllParcels() {
        return parcelService.getAllParcels();
    }

    @GetMapping("/{id}")
    public Parcel getParcelById(@PathVariable Long id) {
        return parcelService.getParcelById(id);
    }

    @PutMapping("/{id}/status")
    public Parcel updateParcelStatus(@PathVariable Long id, @RequestParam String status) {
        return parcelService.updateParcelStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteParcel(@PathVariable Long id) {
        parcelService.deleteParcel(id);
    }
}
