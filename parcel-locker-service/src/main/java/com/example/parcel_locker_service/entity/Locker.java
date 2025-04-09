package com.example.parcel_locker_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Locker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    private int smallSlots;
    private int mediumSlots;
    private int largeSlots;

    @OneToMany(mappedBy = "locker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LockerSlot> slots = new ArrayList<>();
}
