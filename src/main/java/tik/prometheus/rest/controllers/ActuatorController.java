package tik.prometheus.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tik.prometheus.rest.dtos.ActuatorDTO;
import tik.prometheus.rest.dtos.ActuatorLiteDTO;
import tik.prometheus.rest.models.Actuator;
import tik.prometheus.rest.repositories.ActuatorRepos;
import tik.prometheus.rest.services.ActuatorService;

@RestControllerAdvice
@RequestMapping("/actuators")
public class ActuatorController {
    @Autowired
    ActuatorRepos actuatorRepos;

    @Autowired
    ActuatorService service;

    @GetMapping()
    public ResponseEntity<Page<ActuatorLiteDTO>> all(Pageable pageable, @RequestParam Long greenhouseId) {
        return ResponseEntity.ok(service.getActuators(greenhouseId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActuatorDTO> one(@PathVariable Long id) {
        return ResponseEntity.ok(service.getActuator(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actuator> replace(@PathVariable Long id, @RequestBody Actuator updateActuator) {
        return actuatorRepos.findById(id).map(actuator -> {
            actuator.setType(updateActuator.getType());
            actuator.setUnit(updateActuator.getUnit());
            return ResponseEntity.ok(actuatorRepos.save(actuator));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateState(@PathVariable Long id, @RequestBody ActuatorDTO.ActuatorState nextState) {
        service.patchActuator(id, nextState);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        actuatorRepos.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
