package com.backend.aggregation_service.dao;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.backend.aggregation_service.dto.ZoneKpiDto;
import com.backend.aggregation_service.dto.ZoneLoadLevelDto;
import com.backend.aggregation_service.dto.ZonePeriodSummaryDto;
import com.backend.aggregation_service.dto.ZoneSummaryDto;
import com.backend.aggregation_service.dto.ZoneTimeseriesDto;

@Repository
public interface AggregationServiceDao {
    // TODO get data for now (last 1-5 minutes) for a zone, (for many zones?) from all nodes in that zone
    // get data for specific day for zone
    /** Compute load levels for zones given thresholds */
    /** KPI analytics: average, min, max over a time range */
    /** Historical aggregation per period (e.g. hourly, daily averages) */



    /** Return latest snapshot per zone */
    List<ZoneSummaryDto> getLatestSummary(Duration window);

    /** Get historical trend for a specific zone */
    List<ZoneTimeseriesDto> getZoneTimeseries(int zoneId, Duration window, Duration step);

    /** Compute load levels for zones given thresholds */
    List<ZoneLoadLevelDto> getZonesLoadLevel(Duration window);

    /** KPI analytics: average, min, max over a time range */
    ZoneKpiDto getZoneKpis(int zoneId, Instant start, Instant end);

    /** Historical aggregation per period (e.g. hourly, daily averages) */
    List<ZonePeriodSummaryDto> getZoneHistory(int zoneId, String period); // "hour", "day"

    // --- Maintenance / Utils ---

    /** Delete old data based on retention policy */
    void deleteOldMetrics(Instant olderThan);

    /** Count total records stored (for monitoring) */
    long countRecords();

}
