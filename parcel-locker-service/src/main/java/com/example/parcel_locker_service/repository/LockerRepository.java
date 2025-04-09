package com.example.parcel_locker_service.repository;

import com.example.parcel_locker_service.entity.Locker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LockerRepository extends JpaRepository<Locker, Long> {
    @Query("SELECT l FROM Locker l LEFT JOIN FETCH l.slots WHERE l.id = :id")
    Optional<Locker> findByIdWithSlots(@Param("id") Long id);
}
