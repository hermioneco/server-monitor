/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tg.univlome.epl.cyberwatch.model;

/**
 *
 * @author ashie
 */


import java.time.LocalDateTime;

public class Incident {
    private Long id;
    private String title;
    private String description;
    private String severity;
    private String status;
    private String sourceIp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructeurs
    public Incident() {}

    public Incident(String title, String description, 
                    String severity, String sourceIp) {
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.sourceIp = sourceIp;
        this.status = "OPEN";
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSourceIp() { return sourceIp; }
    public void setSourceIp(String sourceIp) { this.sourceIp = sourceIp; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    
}
