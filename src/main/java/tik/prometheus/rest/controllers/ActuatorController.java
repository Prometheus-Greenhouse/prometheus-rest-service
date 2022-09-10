package tik.prometheus.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tik.prometheus.rest.models.Actuator;
import tik.prometheus.rest.repositories.ActuatorRepos;

@RestControllerAdvice
@RequestMapping("/actuators")
public class ActuatorController {
    @Autowired
    ActuatorRepos actuatorRepos;

    @PostMapping()
    public ResponseEntity<Actuator> post(Actuator actuator) {
        actuatorRepos.save(actuator);
        return new ResponseEntity<>(actuator, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Page<Actuator>> all(Pageable pageable) {
        return ResponseEntity.ok(actuatorRepos.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actuator> one(@PathVariable Long id) {
        return actuatorRepos.findById(id).
                map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actuator> replace(@PathVariable Long id, @RequestBody Actuator updateActuator) {
        return actuatorRepos.findById(id).map(actuator -> {
            actuator.setType(updateActuator.getType());
            actuator.setUnit(updateActuator.getUnit());
            return ResponseEntity.ok(actuatorRepos.save(actuator));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        actuatorRepos.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
