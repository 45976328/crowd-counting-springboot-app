package com.backend.zones_service.dto;

import java.util.Map;

import lombok.Data;

@Data
public class NodeDto {
    private int id; //TODO change to node_id
    private int zone_id;
    private String mac;
    private String lat;
    private String longitude;
    private Map<String, Object> config;
}
