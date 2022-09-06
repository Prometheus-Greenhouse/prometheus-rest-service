package tik.prometheus.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tik.prometheus.rest.dtos.SensorDTO;
import tik.prometheus.rest.models.Sensor;
import tik.prometheus.rest.repositories.SensorRepos;
import tik.prometheus.rest.services.SensorService;

@RestControllerAdvice
@RestController
@RequestMapping("/sensors")
public class SensorController {

    @Autowired
    SensorRepos sensorRepos;
    @Autowired
    SensorService sensorService;

    @GetMapping()
    public ResponseEntity<Page<SensorDTO>> getSensors(Pageable pageable) {
        return ResponseEntity.ok(sensorService.getSensors(pageable));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Sensor> updateSensor(@PathVariable Long id, @RequestBody Sensor updateSensor) {
        return sensorRepos.findById(id).map(sensor -> {
            sensor.setAddress(updateSensor.getAddress());
            sensor.setType(updateSensor.getType());
            sensor.setUnit(updateSensor.getUnit());
            return ResponseEntity.ok(sensorRepos.save(sensor));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
