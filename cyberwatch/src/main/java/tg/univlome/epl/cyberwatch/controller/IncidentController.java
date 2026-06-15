/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tg.univlome.epl.cyberwatch.controller;

/**
 *
 * @author ashie
 */


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.univlome.epl.cyberwatch.model.Incident;
import tg.univlome.epl.cyberwatch.service.IncidentService;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService service;

    public IncidentController(IncidentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Incident> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incident> getById(@PathVariable Long id) {
        return service.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/severity/{level}")
    public List<Incident> getBySeverity(@PathVariable String level) {
        return service.getBySeverity(level);
    }

    @PostMapping
    public ResponseEntity<Incident> create(@RequestBody Incident incident) {
        return ResponseEntity.status(201).body(service.create(incident));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody Incident incident) {
        return service.update(id, incident)
            ? ResponseEntity.ok().build()
            : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        return service.getStats();
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok", "app", "CyberWatch");
    }
}