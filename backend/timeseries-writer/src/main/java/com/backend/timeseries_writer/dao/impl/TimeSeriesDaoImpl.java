package com.backend.timeseries_writer.dao.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.backend.timeseries_writer.dto.DataFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimeSeriesDaoImpl {

    //@RequiredArgsConstructor (from Lombok) only generates a constructor for final or @NonNull fields.
    // If your mapper field is not final and also not annotated with @NonNull, Lombok won’t include it in the generated constructor — so Spring won’t inject it.
    // That means mapper will remain null, and when you call mapper.convertValue(...), you’ll get the exact NullPointerException you saw.
    private final ObjectMapper mapper;

    @KafkaListener(topics = "data")
    public void getDatafromKafka(List<DataFormat> data){
        //System.out.println(data);

        for(Object item : data){
            // Convert each map into DataFormat DTO
            DataFormat df = mapper.convertValue(item, DataFormat.class);
            writeToDatabase(df);
        }
    }

    private final InfluxDBClient influx;

    @Value("${influx.bucket}") private String bucket;
    @Value("${influx.org}")    private String org;

    @SuppressWarnings("null")
    public void writeToDatabase(DataFormat data){
        //System.out.println(data);

        Point p = Point.measurement("crowd_monitor")
            .addTag("nodeId", data.getNodeId())
            .addTag("zoneId", data.getZoneId())
            .addTag("mac", data.getMac())
            .addField("avgRssi", data.getRssi().stream().mapToDouble(Double::doubleValue).average().orElse(0))
            .addField("countRssi", data.getRssi().size())
            .time(Instant.parse(data.getTimestamp()), WritePrecision.MS);
            
        try (WriteApi writeApi = influx.makeWriteApi()) {
            writeApi.writePoint(bucket, org, p);
            System.out.println("Published to Influx : " + p);
        }catch(Exception e){
            System.err.println("Couldn't publish to Influx");
        }
    }
}
