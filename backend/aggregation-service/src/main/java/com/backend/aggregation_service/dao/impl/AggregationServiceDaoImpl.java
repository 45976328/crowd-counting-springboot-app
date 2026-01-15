package com.backend.aggregation_service.dao.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.aggregation_service.dao.AggregationServiceDao;
import com.backend.aggregation_service.dto.ZoneKpiDto;
import com.backend.aggregation_service.dto.ZoneLoadLevelDto;
import com.backend.aggregation_service.dto.ZonePeriodSummaryDto;
import com.backend.aggregation_service.dto.ZoneSummaryDto;
import com.backend.aggregation_service.dto.ZoneTimeseriesDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AggregationServiceDaoImpl implements AggregationServiceDao{
    
    @Override
    public List<ZoneSummaryDto> getLatestSummary(Duration window) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLatestSummary'");
    }

    @Override
    public List<ZoneTimeseriesDto> getZoneTimeseries(int zoneId, Duration window, Duration step) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getZoneTimeseries'");
    }

    @Override
    public List<ZoneLoadLevelDto> getZonesLoadLevel(Duration window) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getZonesLoadLevel'");
    }

    @Override
    public ZoneKpiDto getZoneKpis(int zoneId, Instant start, Instant end) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getZoneKpis'");
    }

    @Override
    public List<ZonePeriodSummaryDto> getZoneHistory(int zoneId, String period) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getZoneHistory'");
    }

    @Override
    public void deleteOldMetrics(Instant olderThan) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteOldMetrics'");
    }

    @Override
    public long countRecords() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'countRecords'");
    }

    
}
