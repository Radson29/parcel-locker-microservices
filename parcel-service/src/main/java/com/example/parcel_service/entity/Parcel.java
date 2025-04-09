package com.example.parcel_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long lockerId;
    private String recipientName;
    private String recipientAddress;

    @Enumerated(EnumType.STRING)
    private ParcelStatus status;

    @Enumerated(EnumType.STRING)
    private ParcelSize size;
}