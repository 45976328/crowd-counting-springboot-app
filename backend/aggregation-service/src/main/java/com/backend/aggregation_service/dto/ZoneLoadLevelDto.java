package com.backend.aggregation_service.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class ZoneLoadLevelDto {
    private int zoneId;
    private String level; // normal, busy, high
    private int devicesTotal;
    private Instant updatedAt;
}
