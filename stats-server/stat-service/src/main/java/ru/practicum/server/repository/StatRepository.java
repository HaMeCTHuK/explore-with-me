package ru.practicum.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.server.model.HitEntity;
import ru.practicum.server.model.StatEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<HitEntity, Long> {
    @Query(" SELECT new ru.practicum.server.model.StatEntity(h.app, h.uri, COUNT(h.ip) AS hits) " +
            "FROM HitEntity h " +
            "WHERE h.timestamp between ?1 AND ?2 " +
            "AND h.uri IN(?3) " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC ")
    List<StatEntity> findStat(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(" SELECT new ru.practicum.server.model.StatEntity(h.app, h.uri, COUNT(DISTINCT h.ip) AS hits) " +
            "FROM HitEntity h " +
            "WHERE h.timestamp between ?1 AND ?2 " +
            "AND h.uri IN(?3) " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(DISTINCT h.ip) DESC ")
    List<StatEntity> findUniqueStat(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(" SELECT new ru.practicum.server.model.StatEntity(h.app, h.uri, COUNT(h.ip) AS hits) " +
            "FROM HitEntity h " +
            "WHERE h.timestamp between ?1 AND ?2 " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC ")
    List<StatEntity> findAllStats(LocalDateTime start, LocalDateTime end);

    @Query(" SELECT new ru.practicum.server.model.StatEntity(h.app, h.uri, COUNT(DISTINCT h.ip) AS hits) " +
            "FROM HitEntity h " +
            "WHERE h.timestamp between ?1 AND ?2 " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(DISTINCT h.ip) DESC ")
    List<StatEntity> findAllUniqueStats(LocalDateTime start, LocalDateTime end);
}
