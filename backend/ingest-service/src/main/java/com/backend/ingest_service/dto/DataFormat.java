package com.backend.ingest_service.dto;

import java.util.List;

import lombok.Data;

@Data
public class DataFormat {
    private String nodeId; //TODO INT
    private String zoneId; //TODO INT

    private String mac;
    private List<Double> rssi;
    private String timestamp;

}
// https://stackoverflow.com/questions/1051182/what-is-a-data-transfer-object-dto