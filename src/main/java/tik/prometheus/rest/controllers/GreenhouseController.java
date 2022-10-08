package tik.prometheus.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tik.prometheus.rest.dtos.GreenhouseDTO;
import tik.prometheus.rest.dtos.GreenhouseSummaryDTO;
import tik.prometheus.rest.models.Greenhouse;
import tik.prometheus.rest.repositories.GreenhouseRepos;
import tik.prometheus.rest.services.GreenhouseService;

@RestControllerAdvice
@RequestMapping("/greenhouses")
public class GreenhouseController {
    @Autowired
    GreenhouseRepos repos;

    @Autowired
    GreenhouseService service;

    @PostMapping()
    public ResponseEntity<Greenhouse> post(Greenhouse greenhouse) {
        repos.save(greenhouse);
        return new ResponseEntity<>(greenhouse, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Page<GreenhouseSummaryDTO>> all(@RequestParam(required = false) Long farmId, Pageable pageable) {
        return ResponseEntity.ok(service.getGreenhouses(farmId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GreenhouseDTO> one(@PathVariable Long id) {
        return ResponseEntity.ok(service.getGreenhouse(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Greenhouse> replace(@PathVariable Long id, @RequestBody Greenhouse updateGH) {
        return repos.findById(id).map(greenhouse -> {
            greenhouse.setType(updateGH.getType());
            greenhouse.setArea(updateGH.getArea());
            greenhouse.setCultivationArea(updateGH.getCultivationArea());
            greenhouse.setHeight(updateGH.getHeight());
            greenhouse.setWidth(updateGH.getWidth());
            greenhouse.setLength(updateGH.getLength());
            return ResponseEntity.ok(repos.save(greenhouse));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repos.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
