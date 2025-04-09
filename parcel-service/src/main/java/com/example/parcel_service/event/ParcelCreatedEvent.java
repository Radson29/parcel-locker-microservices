package com.example.parcel_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParcelCreatedEvent {
    private Long parcelId;
    private Long userId;
    private String lockerId;
    private String status;
    private String parcelSize;
}
