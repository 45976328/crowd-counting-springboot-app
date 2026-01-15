package com.backend.ingest_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.ingest_service.dao.impl.DataIngestDaoImpl;
import com.backend.ingest_service.dto.DataFormat;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/ingest")
public class IngestController {
    // Injects the DataIngestDaoImpl component that handles
    // publishing data to Kafka. Constructor injection is preferred
    // for required dependencies since it ensures immutability and easier testing.
    private final DataIngestDaoImpl dataIngestDaoImpl;
    /**
     * Constructor-based dependency injection.
     *
     * You’re telling Spring:
     * “I can’t create this controller unless you give me a DataIngestDaoImpl object.”
     *
     * Because:
     * - DataIngestDaoImpl is (likely) annotated with @Service,
     *   so Spring knows how to construct it and registers it as a bean.
     *
     * When the application starts:
     * 1. Spring scans all packages for annotated classes (via @ComponentScan).
     * 2. It finds IngestController (a @RestController) and DataIngestDaoImpl (a @Repository or @Component).
     * 3. Spring tries to instantiate IngestController.
     * 4. It sees that the constructor requires a DataIngestDaoImpl.
     * 5. It looks up an existing DataIngestDaoImpl bean and injects it into the constructor.
     *
     * That’s who calls this constructor — Spring itself (the IoC container).
     * You never call new IngestController() manually.
     */

    // Constructor-based dependency injection (no @Autowired needed)
    IngestController(DataIngestDaoImpl dataIngestDaoImpl){
        this.dataIngestDaoImpl = dataIngestDaoImpl;
    }

    @PostMapping("/publish")
    public ResponseEntity<?> handleIncomingData(@RequestBody List<DataFormat> data) {

        System.out.println("Received data : "+ data);

        // Forward the incoming data to Kafka through the DAO layer
        dataIngestDaoImpl.publishToKafka(data);

        return ResponseEntity.ok(Map.of("status", "ok"));
    }
    

}
