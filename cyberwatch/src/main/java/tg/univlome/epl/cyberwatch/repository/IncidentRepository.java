/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tg.univlome.epl.cyberwatch.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import tg.univlome.epl.cyberwatch.model.*;

@Repository
public class IncidentRepository {

    private final JdbcTemplate jdbc;

    public IncidentRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // RowMapper — convertit un ResultSet en Incident
    private Incident mapRow(ResultSet rs, int rowNum) throws SQLException {
        Incident incident = new Incident();
        incident.setId(rs.getLong("id"));
        incident.setTitle(rs.getString("title"));
        incident.setDescription(rs.getString("description"));
        incident.setSeverity(rs.getString("severity"));
        incident.setStatus(rs.getString("status"));
        incident.setSourceIp(rs.getString("source_ip"));
        incident.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        incident.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return incident;
    }

    // CREATE — PreparedStatement avec retour de la clé générée
    public Incident save(Incident incident) {
        String sql = """
            INSERT INTO incidents (title, description, severity, status, source_ip)
            VALUES (?, ?, ?, ?, ?)
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, incident.getTitle());
            ps.setString(2, incident.getDescription());
            ps.setString(3, incident.getSeverity());
            ps.setString(4, incident.getStatus() != null ? incident.getStatus() : "OPEN");
            ps.setString(5, incident.getSourceIp());
            return ps;
        }, keyHolder);

        incident.setId(((Number) keyHolder.getKeys().get("id")).longValue());
        return incident;
    }

    // READ ALL
    public List<Incident> findAll() {
        String sql = "SELECT * FROM incidents ORDER BY created_at DESC";
        return jdbc.query(sql, this::mapRow);
    }

    // READ BY ID
    public Optional<Incident> findById(Long id) {
        String sql = "SELECT * FROM incidents WHERE id = ?";
        List<Incident> results = jdbc.query(sql, this::mapRow, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    // READ BY SEVERITY
    public List<Incident> findBySeverity(String severity) {
        String sql = "SELECT * FROM incidents WHERE severity = ? ORDER BY created_at DESC";
        return jdbc.query(sql, this::mapRow, severity.toUpperCase());
    }

    // UPDATE
    public boolean update(Long id, Incident incident) {
        String sql = """
            UPDATE incidents
            SET title = ?, description = ?, severity = ?,
                status = ?, source_ip = ?, updated_at = NOW()
            WHERE id = ?
            """;

        int rows = jdbc.update(sql,
            incident.getTitle(),
            incident.getDescription(),
            incident.getSeverity(),
            incident.getStatus(),
            incident.getSourceIp(),
            id);

        return rows > 0;
    }

    // DELETE
    public boolean delete(Long id) {
        String sql = "DELETE FROM incidents WHERE id = ?";
        return jdbc.update(sql, id) > 0;
    }

    // STATS
    public java.util.Map<String, Object> getStats() {
        String sql = """
            SELECT
                COUNT(*) as total,
                SUM(CASE WHEN severity = 'CRITICAL' THEN 1 ELSE 0 END) as critical,
                SUM(CASE WHEN severity = 'HIGH' THEN 1 ELSE 0 END) as high,
                SUM(CASE WHEN severity = 'MEDIUM' THEN 1 ELSE 0 END) as medium,
                SUM(CASE WHEN severity = 'LOW' THEN 1 ELSE 0 END) as low,
                SUM(CASE WHEN status = 'OPEN' THEN 1 ELSE 0 END) as open_count,
                SUM(CASE WHEN status = 'RESOLVED' THEN 1 ELSE 0 END) as resolved
            FROM incidents
            """;
        return jdbc.queryForMap(sql);
    }
}