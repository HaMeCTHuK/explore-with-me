package ru.practicum.server.service;

import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {
    void addHit(HitDto hitDto);

    List<StatDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
