package ru.practicum.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatDto;
import ru.practicum.server.exception.BadRequestException;
import ru.practicum.server.mapper.HitMapper;
import ru.practicum.server.mapper.StatMapper;
import ru.practicum.server.model.StatEntity;
import ru.practicum.server.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;

    @Override
    @Transactional
    public void addHit(HitDto hitDto) {
        log.info("Adding hit: {}", hitDto);
        statRepository.save(HitMapper.toHit(hitDto));
        log.info("Hit added successfully.");
    }

    @Override
    public List<StatDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        log.info("Fetching statistics from {} to {}. URIs: {}, Unique: {}", start, end, uris, unique);
        if (start.isAfter(end)) {
            throw new BadRequestException("Время окончания должно быть позднее времени начала!");
        }
        List<StatEntity> stat;

        if (uris == null || uris.isEmpty()) {
            if (unique) {
                stat = statRepository.findAllUniqueStats(start, end);
            } else {
                stat = statRepository.findAllStats(start, end);
            }
        } else {
            if (unique) {
                stat = statRepository.findUniqueStat(start, end, uris);
            } else {
                stat = statRepository.findStat(start, end, uris);
            }
        }

        List<StatDto> result = stat.stream().map(StatMapper::toStatDto).collect(Collectors.toList());
        log.info("Fetched {} statistics.", result.size());
        return result;
    }
}
