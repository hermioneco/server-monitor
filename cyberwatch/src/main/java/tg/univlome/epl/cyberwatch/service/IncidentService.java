/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tg.univlome.epl.cyberwatch.service;

/**
 *
 * @author ashie
 */

import org.springframework.stereotype.Service;
import tg.univlome.epl.cyberwatch.model.Incident;
import tg.univlome.epl.cyberwatch.repository.IncidentRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class IncidentService {

    private final IncidentRepository repository;

    public IncidentService(IncidentRepository repository) {
        this.repository = repository;
    }

    public Incident create(Incident incident) {
        validateSeverity(incident.getSeverity());
        return repository.save(incident);
    }

    public List<Incident> getAll() {
        return repository.findAll();
    }

    public Optional<Incident> getById(Long id) {
        return repository.findById(id);
    }

    public List<Incident> getBySeverity(String severity) {
        validateSeverity(severity);
        return repository.findBySeverity(severity);
    }

    public boolean update(Long id, Incident incident) {
        validateSeverity(incident.getSeverity());
        return repository.update(id, incident);
    }

    public boolean delete(Long id) {
        return repository.delete(id);
    }

    public Map<String, Object> getStats() {
        return repository.getStats();
    }

    private void validateSeverity(String severity) {
        List<String> valid = List.of("LOW", "MEDIUM", "HIGH", "CRITICAL");
        if (!valid.contains(severity.toUpperCase())) {
            throw new IllegalArgumentException(
                "Sévérité invalide. Valeurs acceptées : " + valid);
        }
    }
}
