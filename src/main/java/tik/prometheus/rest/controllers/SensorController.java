package tik.prometheus.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tik.prometheus.rest.dtos.SensorDTO;
import tik.prometheus.rest.models.Sensor;
import tik.prometheus.rest.repositories.SensorRepos;
import tik.prometheus.rest.services.SensorService;

import java.util.List;

@RestControllerAdvice
@RestController
@RequestMapping("/sensors")
public class SensorController {

    @Autowired
    SensorRepos sensorRepos;
    @Autowired
    SensorService sensorService;

    @GetMapping()
    public ResponseEntity<List<SensorDTO>> getSensors() {
        return ResponseEntity.ok(sensorService.getSensors());
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
