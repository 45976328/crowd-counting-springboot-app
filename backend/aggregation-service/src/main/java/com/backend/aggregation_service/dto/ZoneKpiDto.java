package com.backend.aggregation_service.dto;

import lombok.Data;

@Data
public class ZoneKpiDto {
    private int zoneId;
    private double avgDevices;
    private int minDevices;
    private int maxDevices;
    private double avgRssi;
}
