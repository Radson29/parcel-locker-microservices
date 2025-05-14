package com.example.delivery_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParcelStatusUpdatedEvent {
    private Long parcelId;
    private String status;
}
