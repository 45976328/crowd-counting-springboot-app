package com.backend.timeseries_writer.dto;

import java.util.List;

import lombok.Data;

@Data
public class DataFormat {
    private String nodeId; //TODO INT
    private String zoneId; // TODO INT

    private String mac;
    private List<Double> rssi;
    private String timestamp;

}