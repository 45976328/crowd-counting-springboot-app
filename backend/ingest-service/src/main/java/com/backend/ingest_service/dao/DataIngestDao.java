package com.backend.ingest_service.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.backend.ingest_service.dto.DataFormat;

@Repository
public interface DataIngestDao { //DAO has CRUD actions like save, update, and remove.
    void publishToKafka(final List<DataFormat> data);
    
}
