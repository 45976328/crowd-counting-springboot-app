package com.backend.aggregation_service.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class ZoneTimeseriesDto {
    private Instant timestamp;
    private int zoneId;
    private int devicesTotal;
    private int wifi;
    private double rssiAvg;
}
