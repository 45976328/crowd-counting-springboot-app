package com.backend.zones_service.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.backend.zones_service.dto.NodeDto;
import com.backend.zones_service.dto.ZoneDto;
import com.backend.zones_service.dto.ZoneThresholdsDto;

@Repository
public interface ZonesDao {
    //CRUD
    int createZone(ZoneDto zone);
    ZoneDto readZone(int id);
    int updateZone(ZoneDto zone);
    // List<ZoneDto> readAllZones();
    List<Integer> readRegisteredZoneIdsList();
    int deleteZone(int id);

    int createZoneThresholds(ZoneThresholdsDto zoneThresholdDto);
    ZoneThresholdsDto readZoneThresholds(int id);
    // List<ZoneThresholdsDto> readAllZoneThresholds();
    int updateZoneThresholds(ZoneThresholdsDto zoneThresholdDto);
    int deleteZoneThresholds(int id);

    int createNode(NodeDto node);
    NodeDto readNode(int id);
    // List<NodeDto> readAllNodes();
    List<Integer> readRegisteredNodeIdsList();
    int updateNode(NodeDto node);
    int deleteNode(int id);

}
