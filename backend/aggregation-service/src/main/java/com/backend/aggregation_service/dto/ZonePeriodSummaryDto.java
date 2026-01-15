package com.backend.aggregation_service.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class ZonePeriodSummaryDto {
    private int zoneId;
    private String period; // "hour" or "day"
    private Instant start;
    private Instant end;
    private double avgDevices;
}
