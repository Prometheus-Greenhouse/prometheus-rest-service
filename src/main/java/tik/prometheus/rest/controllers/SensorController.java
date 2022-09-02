package tik.prometheus.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tik.prometheus.rest.Configurations;
import tik.prometheus.rest.models.Sensor;
import tik.prometheus.rest.repositories.SensorRecordRepos;
import tik.prometheus.rest.repositories.SensorRepos;

import java.util.List;

@RestControllerAdvice
@RestController
public class SensorController {
    @Autowired
    Configurations configurations;

    @Autowired
    SensorRecordRepos sensorRecordRepos;

    @Autowired
    SensorRepos sensorRepos;

    @GetMapping("/hello")
    public Configurations getStuff() {
        return configurations;
    }

    @GetMapping("/test-db")
    public List<Sensor> testDb() {
        return sensorRepos.findAll();
    }
}
