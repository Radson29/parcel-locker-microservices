package com.example.parcel_locker_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LockerSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int slotNumber;

    @Enumerated(EnumType.STRING)
    private LockerSlotSize size;

    @Enumerated(EnumType.STRING)
    private LockerSlotStatus status;

    @ManyToOne
    @JoinColumn(name = "locker_id")
    private Locker locker;
}

