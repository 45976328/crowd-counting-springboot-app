package com.backend.zones_service.dto;

import lombok.Data;

@Data
public class ZoneThresholdsDto {
    private int zone_id;
    private String level;  // "normal", "busy", "high"
    private int min;
    private Integer max; // (nullable)
}
