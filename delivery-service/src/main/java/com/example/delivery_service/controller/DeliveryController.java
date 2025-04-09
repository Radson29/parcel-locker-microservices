package com.example.delivery_service.controller;


import com.example.delivery_service.entity.Delivery;
import com.example.delivery_service.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
        return ResponseEntity.ok(deliveryService.createDelivery(delivery));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.getDeliveryById(id));
    }
    @PutMapping("/{id}/complete")
    public ResponseEntity<Delivery> completeDelivery(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.completeDelivery(id));
    }
}
