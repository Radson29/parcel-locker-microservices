package com.example.courier_service.repository;

import com.example.courier_service.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourierRepository extends JpaRepository<Courier, Long> {
    List<Courier> findByAvailable(boolean available);
    Optional<Courier> findFirstByAvailableTrue();
}