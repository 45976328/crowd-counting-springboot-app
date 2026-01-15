package com.backend.zones_service.controller;

import java.util.List;
import java.util.Map;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.zones_service.dao.impl.ZonesDaoImpl;
import com.backend.zones_service.dto.NodeDto;
import com.backend.zones_service.dto.ZoneDto;
import com.backend.zones_service.dto.ZoneThresholdsDto;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ZonesServiceController {
    private final ZonesDaoImpl zonesDaoImpl;
    // GET: Retrieve a resource (e.g., fetching user details).
    // POST: Create a resource (e.g., adding a new user).
    // PUT: Update a resource (e.g., modifying user information).
    // DELETE: Remove a resource (e.g., deleting a user).

    // ZONE
    @GetMapping("/zone/{id}")
    public ResponseEntity<?> getZone(@PathVariable int id) { 
        try{
            ZoneDto result = zonesDaoImpl.readZone(id);
            return ResponseEntity.ok( Map.of( "zone_id", result.getZone_id(), "category", result.getCategory(), "properties", result.getProperties() ) ); // map of ZoneDto
        }catch(EmptyResultDataAccessException e){
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "No Match"));
        }
    }

    @GetMapping("/zone/get/ids")
    public ResponseEntity<?> getRegisteredZoneIdsList() {
        List<Integer> result = zonesDaoImpl.readRegisteredZoneIdsList();
        return ResponseEntity.ok(Map.of("Id's : ",result));
    }

    @PostMapping("/zone")
    public ResponseEntity<?> postZone(@RequestBody ZoneDto entity) { // add new zone
        int result = zonesDaoImpl.createZone(entity);
        if(result == 1){
            return ResponseEntity.ok(Map.of("status", "ok"));
        }else{
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Couldn't insert to Database"));
        }
        
    }

    @PutMapping("/zone/{id}")
    public ResponseEntity<?> putZone(@PathVariable int id, @RequestBody ZoneDto entity) { // update zone
        
            int result = zonesDaoImpl.updateZone(entity);
            if(result == 1){
                return ResponseEntity.ok(Map.of("status", "ok"));
            }
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(Map.of("error", "Couldn't update Database"));      
    }

    @DeleteMapping("/zone/{id}")
    public ResponseEntity<?> deleteZone(@PathVariable int id) {
        int result = zonesDaoImpl.deleteZone(id);
        if(result == 1){
            return ResponseEntity.ok(Map.of("status", "ok"));
        }
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(Map.of("error", "Couldn't delete"));
        
    }
    

    // ZONE THRESHOLD
    
    @GetMapping("/zone/threshold/{id}")
    public  ResponseEntity<?> getZoneThreshold(@PathVariable int id) {

        try{
            ZoneThresholdsDto result = zonesDaoImpl.readZoneThresholds(id);
            return ResponseEntity.ok(Map.of("zone_id", result.getZone_id(), "level", result.getLevel(), "min", result.getMin(), "max", result.getMax() ) );
        }catch( EmptyResultDataAccessException e){
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                                .body(Map.of("error", "No Match"));
        }
        
    }

    @PostMapping("/zone/threshold")
    public ResponseEntity<?> postZoneThreshold(@RequestBody ZoneThresholdsDto entity) {
        int result = zonesDaoImpl.createZoneThresholds(entity);
        if (result == 1){
            return ResponseEntity.ok(Map.of("status", "ok"));
        }
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(Map.of("error", "Couldn't insert to Database"));
    }
    
    @PutMapping("/zone/threshold/{id}")
    public ResponseEntity<?> putZoneThreshold(@PathVariable int id, @RequestBody ZoneThresholdsDto entity) {
        int result = zonesDaoImpl.updateZoneThresholds(entity);
        if(result == 1){
                return ResponseEntity.ok(Map.of("status", "ok"));
        }
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(Map.of("error", "Couldn't update Database"));  
    }

    @DeleteMapping("/zone/threshold/{id}")
    public ResponseEntity<?> deleteZoneThreshold(@PathVariable int id){
        int result = zonesDaoImpl.deleteZoneThresholds(id);
        if(result == 1){
            return ResponseEntity.ok(Map.of("status", "ok"));
        }
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(Map.of("error", "Couldn't delete"));
    }


    // NODE
    
    @GetMapping("/node/{id}")
    public ResponseEntity<?> getNode(@PathVariable int id) {
        try {
            NodeDto result = zonesDaoImpl.readNode(id);
            return ResponseEntity.ok(Map.of("node_id", result.getId(), "zone_id", result.getZone_id(),"mac", result.getMac(), 
                "latitude", result.getLat(), "logituted", result.getLongitude(), "config", result.getConfig()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(Map.of("error", "No Match"));
        }
    }

    @GetMapping("/node/get/ids")
    public ResponseEntity<?> getRegisteredNodeIdsList() {
       List<Integer> result = zonesDaoImpl.readRegisteredNodeIdsList();
        return ResponseEntity.ok(Map.of("Id's : ",result));
    } 
    
    @PostMapping("/node")
    public ResponseEntity<?> postNode(@RequestBody NodeDto entity) {
        int result = zonesDaoImpl.createNode(entity);
        if (result == 1){
            return ResponseEntity.ok(Map.of("status", "ok"));
        }
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(Map.of("error", "Couldn't insert to Database"));
    }
    
    @PutMapping("node/{id}")
    public ResponseEntity<?> putNode(@PathVariable String id, @RequestBody NodeDto entity) {
        int result = zonesDaoImpl.updateNode(entity);
        if(result == 1){
                return ResponseEntity.ok(Map.of("status", "ok"));
        }
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(Map.of("error", "Couldn't update Database"));  
    }

    @DeleteMapping("/node/{id}")
    public ResponseEntity<?> deleteNode(@PathVariable int id){
        int result = zonesDaoImpl.deleteNode(id);
        if(result == 1){
            return ResponseEntity.ok(Map.of("status", "ok"));
        }
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(Map.of("error", "Couldn't delete"));
    }
}
