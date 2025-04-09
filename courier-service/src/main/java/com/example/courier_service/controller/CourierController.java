package com.example.courier_service.controller;

import com.example.courier_service.entity.Courier;
import com.example.courier_service.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/couriers")
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;

    @PostMapping
    public ResponseEntity<Courier> addCourier(@RequestBody Courier courier) {
        return ResponseEntity.ok(courierService.addCourier(courier));
    }

    @GetMapping
    public ResponseEntity<List<Courier>> getAllCouriers() {
        return ResponseEntity.ok(courierService.getAllCouriers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Courier> getCourierById(@PathVariable Long id) {
        return ResponseEntity.ok(courierService.getCourierById(id));
    }

    @PutMapping("/{id}/availability")
    public ResponseEntity<Courier> updateAvailability(
            @PathVariable Long id,
            @RequestParam boolean available
    ) {
        return ResponseEntity.ok(courierService.updateAvailability(id, available));
    }

    @GetMapping("/available")
    public ResponseEntity<List<Courier>> getAvailableCouriers() {
        return ResponseEntity.ok(courierService.getAvailableCouriers());
    }
}
