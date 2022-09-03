package tik.prometheus.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tik.prometheus.rest.models.Sensor;
import tik.prometheus.rest.repositories.SensorRepos;

import java.util.List;

@RestControllerAdvice
@RestController
@RequestMapping("/sensors")
public class SensorController {

    @Autowired
    SensorRepos sensorRepos;

    @GetMapping()
    public List<Sensor> getSensors() {
        return sensorRepos.findAll();
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
