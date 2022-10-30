package tik.prometheus.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tik.prometheus.rest.dtos.SensorDTO;
import tik.prometheus.rest.dtos.SensorLiteDTO;
import tik.prometheus.rest.repositories.SensorRepos;
import tik.prometheus.rest.services.SensorService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RestControllerAdvice
@RequestMapping("/sensors")
public class SensorController {

    @Autowired
    SensorRepos sensorRepos;
    @Autowired
    SensorService sensorService;

    @GetMapping()
    public ResponseEntity<Page<SensorLiteDTO>> getSensors(Pageable pageable, @RequestParam(required = false) Long greenhouseId) {
        return ResponseEntity.ok(sensorService.getSensors(pageable, greenhouseId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorDTO> one(@PathVariable Long id) {
        return ResponseEntity.ok(sensorService.getSensor(id));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<SensorDTO> replaceSensor(@PathVariable Long id, @RequestBody SensorDTO updateSensor) {
        return ResponseEntity.ok(sensorService.updateSensor(id, updateSensor));
//        return sensorRepos.findById(id).map(sensor -> {
//            sensor.setAddress(updateSensor.getAddress());
//            sensor.setType(updateSensor.getType());
//            sensor.setUnit(updateSensor.getUnit());
//            return ResponseEntity.ok(sensorRepos.save(sensor));
//        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        sensorService.deleteSensor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/records")
    public ResponseEntity getSensorRecords(
            @PathVariable Long id,
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(value = "to", required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return ResponseEntity.ok(sensorService.getSensorRecords(id, from, to));
    }
}
