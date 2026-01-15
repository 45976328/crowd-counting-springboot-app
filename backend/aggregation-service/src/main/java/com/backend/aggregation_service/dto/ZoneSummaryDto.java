package com.backend.aggregation_service.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class ZoneSummaryDto {
    private int zoneId;
    private String zoneName;
    private int devicesTotal;
    private int wifi;
    private double rssiAvg;
    private Instant updatedAt;
}
