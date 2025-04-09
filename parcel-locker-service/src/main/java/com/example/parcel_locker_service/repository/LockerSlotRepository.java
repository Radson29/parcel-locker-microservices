package com.example.parcel_locker_service.repository;

import com.example.parcel_locker_service.entity.LockerSlot;
import com.example.parcel_locker_service.entity.LockerSlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LockerSlotRepository extends JpaRepository<LockerSlot, Long> {
    List<LockerSlot> findByLockerId(Long lockerId);
    List<LockerSlot> findByStatus(LockerSlotStatus status);
    List<LockerSlot> findByLockerIdAndStatus(Long lockerId, LockerSlotStatus status);
}
