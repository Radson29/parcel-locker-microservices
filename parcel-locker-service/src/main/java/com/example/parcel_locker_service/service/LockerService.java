package com.example.parcel_locker_service.service;

import com.example.parcel_locker_service.dto.LockerRequest;
import com.example.parcel_locker_service.entity.Locker;
import com.example.parcel_locker_service.entity.LockerSlot;
import com.example.parcel_locker_service.entity.LockerSlotSize;
import com.example.parcel_locker_service.entity.LockerSlotStatus;
import com.example.parcel_locker_service.exception.LockerFullException;
import com.example.parcel_locker_service.repository.LockerRepository;
import com.example.parcel_locker_service.repository.LockerSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LockerService {

    private final LockerRepository lockerRepository;
    private final LockerSlotRepository lockerSlotRepository;

    public Locker createLocker(LockerRequest request) {
        Locker locker = Locker.builder()
                .location(request.getLocation())
                .smallSlots(request.getSmallSlots())
                .mediumSlots(request.getMediumSlots())
                .largeSlots(request.getLargeSlots())
                .build();

        List<LockerSlot> slots = new ArrayList<>();
        int slotNumber = 1;

        slots.addAll(createSlots(request.getSmallSlots(), LockerSlotSize.SMALL, locker, slotNumber));
        slotNumber += request.getSmallSlots();

        slots.addAll(createSlots(request.getMediumSlots(), LockerSlotSize.MEDIUM, locker, slotNumber));
        slotNumber += request.getMediumSlots();

        slots.addAll(createSlots(request.getLargeSlots(), LockerSlotSize.LARGE, locker, slotNumber));

        locker.setSlots(slots);

        return lockerRepository.save(locker);
    }

    private List<LockerSlot> createSlots(int count, LockerSlotSize size, Locker locker, int startNumber) {
        List<LockerSlot> slots = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            slots.add(LockerSlot.builder()
                    .slotNumber(startNumber + i)
                    .size(size)
                    .status(LockerSlotStatus.FREE)
                    .locker(locker)
                    .build());
        }
        return slots;
    }

    public List<Locker> getAllLockers() {
        return lockerRepository.findAll();
    }

    public Locker getLockerById(Long id) {
        return lockerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locker not found"));
    }

    public List<LockerSlot> getSlotsByLockerId(Long lockerId) {
        return lockerSlotRepository.findByLockerId(lockerId);
    }

    public LockerSlot updateSlotStatus(Long lockerId, Long slotId, LockerSlotStatus status) {
        LockerSlot slot = lockerSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        slot.setStatus(status);
        return lockerSlotRepository.save(slot);
    }

    public List<Locker> getAvailableLockers() {
        return lockerRepository.findAll().stream()
                .filter(locker -> locker.getSlots().stream().anyMatch(slot -> slot.getStatus() == LockerSlotStatus.FREE))
                .toList();
    }
    public List<LockerSlot> getOccupiedSlotsByLockerId(Long lockerId) {
        return lockerSlotRepository.findByLockerId(lockerId).stream()
                .filter(slot -> slot.getStatus() == LockerSlotStatus.OCCUPIED)
                .toList();
    }
    public LockerSlot findAvailableSlot(Long lockerId, LockerSlotSize parcelSize) {
        Locker locker = lockerRepository.findById(lockerId)
                .orElseThrow(() -> new RuntimeException("Locker not found"));

        Optional<LockerSlot> availableSlot = locker.getSlots().stream()
                .filter(slot -> slot.getStatus() == LockerSlotStatus.FREE && slot.getSize() == parcelSize)
                .findFirst();

        return availableSlot.orElseThrow(() -> new LockerFullException("No available slots in locker " + lockerId + " for parcel size " + parcelSize));
    }
}
