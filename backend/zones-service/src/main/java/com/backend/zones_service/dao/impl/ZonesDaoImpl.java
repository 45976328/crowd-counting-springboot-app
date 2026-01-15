package com.backend.zones_service.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.backend.zones_service.dao.ZonesDao;
import com.backend.zones_service.dto.NodeDto;
import com.backend.zones_service.dto.ZoneDto;
import com.backend.zones_service.dto.ZoneThresholdsDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZonesDaoImpl implements ZonesDao { //TODO might change return type to string for better error handling
    private final JdbcTemplate jdbcTemplate;

    // ZONE

    @Override
    public int createZone(ZoneDto zone) {
        ObjectMapper mapper = new ObjectMapper();
        // Use ObjectMapper to convert the Java Map<String, Object> 'properties'
        // into a JSON string before inserting it into the JSONB column.
        // Without this, JdbcTemplate wouldn't know how to store the map properly.

        try {
            return jdbcTemplate.update(
                    "INSERT INTO zones (id, category, properties) VALUES (?, ?, CAST(? AS jsonb))", 
                    zone.getZone_id(), zone.getCategory(), mapper.writeValueAsString(zone.getProperties())
                );
        } catch (DataAccessException e) {
            e.printStackTrace();
            return 0;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ZoneDto readZone(int id) {
        ObjectMapper mapper = new ObjectMapper();
        return jdbcTemplate.queryForObject(
                "SELECT * FROM zones WHERE id = ?",
                (rs, rowNum) -> { // lambda that returns ZoneDto objcet with fields filled correctly with query response
                    ZoneDto zone = new ZoneDto();
                    zone.setZone_id(rs.getInt("id"));
                    zone.setCategory(rs.getString("category"));
                    try {
                        // Use ObjectMapper to convert the JSON string from the 'properties' column
                        // back into a Map<String, Object>. This reverses the serialization done when
                        // saving the ZoneDto, allowing the Java object to work with structured data again.
                        zone.setProperties(mapper.readValue(rs.getString("properties"), new TypeReference<Map<String, Object>>() {}));
                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return zone;
                },
                id // the ? in the query
                );
    }

    @Override
    public List<Integer> readRegisteredZoneIdsList() {
        return jdbcTemplate.queryForList(
            "SELECT id FROM zones WHERE 1=1",
            Integer.class
        );
    }

    @Override
    public int updateZone(ZoneDto zone) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return jdbcTemplate.update(
                "UPDATE zones SET category = ?, properties = CAST(? AS jsonb) WHERE id = ?",
                zone.getCategory(), mapper.writeValueAsString(zone.getProperties()), zone.getZone_id()
            );
        } catch (DataAccessException | JsonProcessingException e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public int deleteZone(int id) {
        return jdbcTemplate.update("DELETE FROM zones WHERE id = ?", id);
    }


    // ZONE THRESHOLDS

    @Override
    public int createZoneThresholds(ZoneThresholdsDto zoneThresholdsDto) {
        return jdbcTemplate.update(
            "INSERT INTO zone_thresholds (zone_id, level, min, max) VALUES (?, ?, ?, ?)",
            zoneThresholdsDto.getZone_id(), zoneThresholdsDto.getLevel(), zoneThresholdsDto.getMin(), zoneThresholdsDto.getMax()
        );
    }

    @Override
    public ZoneThresholdsDto readZoneThresholds(int id) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM zone_thresholds WHERE zone_id = ?",
            (rs, rowNum) -> {
                ZoneThresholdsDto zoneThresholdsDto = new ZoneThresholdsDto();
                zoneThresholdsDto.setZone_id(rs.getInt("zone_id"));
                zoneThresholdsDto.setLevel(rs.getString("level"));
                zoneThresholdsDto.setMin(rs.getInt("min"));
                zoneThresholdsDto.setMax(rs.getInt("max"));
                return zoneThresholdsDto;
            },
            id
        );
    }

    @Override
    public int updateZoneThresholds(ZoneThresholdsDto zoneThresholdsDto) {
        return jdbcTemplate.update(
            "UPDATE zone_thresholds SET level = ?, min = ?, max = ? WHERE zone_id = ?",
            zoneThresholdsDto.getLevel(), zoneThresholdsDto.getMin(), zoneThresholdsDto.getMax(), zoneThresholdsDto.getZone_id()
        );
    }

    @Override
    public int deleteZoneThresholds(int id) {
        return jdbcTemplate.update(
            "DELETE FROM zone_thresholds WHERE zone_id = ?",
            id
        );
    }


    // NODE
    
    @Override
    public int createNode(NodeDto node) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return jdbcTemplate.update(
                "INSERT INTO nodes (id, zone_id, mac, lat, longitude, config) VALUES (?, ?, ?, ?, ?, CAST(? AS JSONB))",
                node.getId(), node.getZone_id(), node.getMac(), node.getLat(), node.getLongitude(), mapper.writeValueAsString(node.getConfig())
            );
        } catch (DataAccessException e) {
            e.printStackTrace();
            return 0;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public NodeDto readNode(int id) {
        ObjectMapper mapper = new ObjectMapper();

        return jdbcTemplate.queryForObject(
            "SELECT * FROM nodes WHERE id = ?",
            (rs, rowNum) -> {
                NodeDto nodeDto = new NodeDto();
                nodeDto.setId(rs.getInt("id"));
                nodeDto.setZone_id(rs.getInt("zone_id"));
                nodeDto.setMac(rs.getString("mac"));
                nodeDto.setLat(rs.getString("lat"));
                nodeDto.setLongitude(rs.getString("longitude"));
                
                try {
                    nodeDto.setConfig(mapper.readValue(rs.getString("config"), new TypeReference<Map<String, Object>>() {} ));
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return nodeDto;
            },
            id
        );
    }

    @Override
    public List<Integer> readRegisteredNodeIdsList() {
        return jdbcTemplate.queryForList(
            "SELECT id FROM nodes WHERE 1=1",
            Integer.class
        );
    }

    @Override
    public int updateNode(NodeDto node) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return jdbcTemplate.update(
                "UPDATE nodes SET zone_id = ?, mac = ?, lat = ?, longitude = ?, config = CAST(? AS JSONB) WHERE id = ?",
                node.getZone_id(), node.getMac(), node.getLat(), node.getLongitude(), mapper.writeValueAsString(node.getConfig()), node.getId()
            );
        } catch (DataAccessException | JsonProcessingException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteNode(int id) {
       return jdbcTemplate.update(
            "DELETE FROM nodes WHERE id = ?", id );
    }

}


// Usefull links for the future

// https://www.geeksforgeeks.org/advance-java/using-a-list-of-values-in-a-jdbctemplate-in-clause-in-spring-jdbc/