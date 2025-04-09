package com.example.parcel_service.repository;

import com.example.parcel_service.entity.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    List<Parcel> findByUserId(Long userId);
}
