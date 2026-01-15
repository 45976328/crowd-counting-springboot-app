package com.backend.ingest_service.dao.impl;

import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.backend.ingest_service.dao.DataIngestDao;
import com.backend.ingest_service.dto.DataFormat;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DataIngestDaoImpl implements DataIngestDao {
    private final KafkaTemplate<String, List<DataFormat>> kafkaTemplate;
    public void publishToKafka(final List<DataFormat> data) {
        kafkaTemplate.send("data", data);
    }

}
