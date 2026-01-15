package com.backend.zones_service.dto;

import java.util.Map;
import lombok.Data;

@Data
public class ZoneDto {
    private int zone_id;
    private String category;
    private Map<String, Object> properties;
}
