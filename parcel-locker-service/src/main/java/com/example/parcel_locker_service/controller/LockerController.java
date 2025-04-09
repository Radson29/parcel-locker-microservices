package com.example.parcel_locker_service.controller;

import com.example.parcel_locker_service.dto.LockerRequest;
import com.example.parcel_locker_service.entity.Locker;
import com.example.parcel_locker_service.entity.LockerSlot;
import com.example.parcel_locker_service.entity.LockerSlotStatus;
import com.example.parcel_locker_service.service.LockerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lockers")
@RequiredArgsConstructor
public class LockerController {

    private final LockerService lockerService;

    @PostMapping
    public ResponseEntity<Locker> createLocker(@RequestBody LockerRequest request) {
        return ResponseEntity.ok(lockerService.createLocker(request));
    }

    @GetMapping
    public ResponseEntity<List<Locker>> getAllLockers() {
        return ResponseEntity.ok(lockerService.getAllLockers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Locker> getLockerById(@PathVariable Long id) {
        return ResponseEntity.ok(lockerService.getLockerById(id));
    }

    @GetMapping("/{lockerId}/slots")
    public ResponseEntity<List<LockerSlot>> getSlotsByLockerId(@PathVariable Long lockerId) {
        return ResponseEntity.ok(lockerService.getSlotsByLockerId(lockerId));
    }

    @PutMapping("/{lockerId}/slots/{slotId}/status")
    public ResponseEntity<LockerSlot> updateSlotStatus(@PathVariable Long lockerId,
                                                       @PathVariable Long slotId,
                                                       @RequestParam LockerSlotStatus status) {
        return ResponseEntity.ok(lockerService.updateSlotStatus(lockerId, slotId, status));
    }

    @GetMapping("/available")
    public ResponseEntity<List<Locker>> getAvailableLockers() {
        return ResponseEntity.ok(lockerService.getAvailableLockers());
    }
    @GetMapping("/{lockerId}/slots/occupied")
    public List<LockerSlot> getOccupiedSlotsByLocker(@PathVariable Long lockerId) {
        return lockerService.getOccupiedSlotsByLockerId(lockerId);
    }
}

