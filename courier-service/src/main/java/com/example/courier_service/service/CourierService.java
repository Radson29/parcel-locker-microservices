package com.example.courier_service.service;

import com.example.courier_service.entity.Courier;
import com.example.courier_service.exception.NoAvailableCourierException;
import com.example.courier_service.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourierService {

    private final CourierRepository courierRepository;

    public Courier addCourier(Courier courier) {
        courier.setAvailable(true);
        return courierRepository.save(courier);
    }

    public List<Courier> getAllCouriers() {
        return courierRepository.findAll();
    }

    public Courier getCourierById(Long id) {
        return courierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Courier not found"));
    }

    public Courier updateAvailability(Long id, boolean available) {
        Courier courier = getCourierById(id);
        courier.setAvailable(available);
        return courierRepository.save(courier);
    }

    public List<Courier> getAvailableCouriers() {
        return courierRepository.findByAvailable(true);
    }

    public void updateCourierAvailability(Long courierId, boolean availability) {
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new RuntimeException("Courier not found with ID: " + courierId));

        courier.setAvailable(availability);
        courierRepository.save(courier);
    }
    public Courier findAvailableCourier() {
        return courierRepository.findFirstByAvailableTrue()
                .orElseThrow(() -> new NoAvailableCourierException("No available couriers found"));
    }
}
