package ru.practicum.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatDto;
import ru.practicum.server.service.StatService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.server.util.DateConstants.DATE_TIME_FORMAT;

@RequiredArgsConstructor
@RestController
@Slf4j
public class StatController {
    private final StatService statService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void addHit(@Valid @RequestBody HitDto hitDto) {
        log.info("Add Hit {}", hitDto);
        statService.addHit(hitDto);
    }

    @GetMapping("/stats")
    public List<StatDto> getStatistics(@RequestParam @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime start,
                                       @RequestParam @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime end,
                                       @RequestParam(required = false) List<String> uris,
                                       @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Get Statistic: start {}, end {}", start, end);
        List<StatDto> stats = statService.getStatistics(start, end, uris, unique);
        log.info("Statistics fetched: {}", stats);
        return stats;
    }
}
