package tik.prometheus.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tik.prometheus.rest.models.Greenhouse;
import tik.prometheus.rest.repositories.GreenhouseRepos;

@RestControllerAdvice
@RequestMapping("/greenhouses")
public class GreenhouseController {
    @Autowired
    GreenhouseRepos farmRepos;

    @PostMapping()
    public ResponseEntity<Greenhouse> post(Greenhouse greenhouse) {
        farmRepos.save(greenhouse);
        return new ResponseEntity<>(greenhouse, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Page<Greenhouse>> all(Pageable pageable) {
        return ResponseEntity.ok(farmRepos.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Greenhouse> one(@PathVariable Long id) {
        return farmRepos.findById(id).
                map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Greenhouse> replace(@PathVariable Long id, @RequestBody Greenhouse updateGH) {
        return farmRepos.findById(id).map(greenhouse -> {
            greenhouse.setType(updateGH.getType());
            greenhouse.setArea(updateGH.getArea());
            greenhouse.setCultivationArea(updateGH.getCultivationArea());
            greenhouse.setHeight(updateGH.getHeight());
            greenhouse.setWidth(updateGH.getWidth());
            greenhouse.setLength(updateGH.getLength());
            return ResponseEntity.ok(farmRepos.save(greenhouse));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        farmRepos.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
