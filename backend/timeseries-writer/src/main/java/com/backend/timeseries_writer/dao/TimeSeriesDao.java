package com.backend.timeseries_writer.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.backend.timeseries_writer.dto.DataFormat;

@Repository
public interface TimeSeriesDao {
    void getDatafromKafka(List<DataFormat> data);
    void saveToDatabase(DataFormat data);
}
