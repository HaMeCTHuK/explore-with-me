package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventDto;
import ru.practicum.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.practicum.util.DateConstants.DATE_TIME_FORMAT;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class EventPublicController {
    private final EventService eventService;

    @GetMapping("/{id}")
    public EventDto getByIdPublic(@PathVariable Long id, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String url = request.getRequestURI();
        return eventService.getPublicEventById(id, url, ip);
    }

    @GetMapping
    public Collection<EventDto> getAllPublic(@RequestParam(defaultValue = "") String text,
                                             @RequestParam(defaultValue = "") List<Long> categories,
                                             @RequestParam(required = false) Boolean paid,
                                             @RequestParam(required = false)
                                                 @DateTimeFormat(pattern = DATE_TIME_FORMAT)
                                                 LocalDateTime rangeStart,
                                             @RequestParam(required = false)
                                                 @FutureOrPresent @DateTimeFormat(pattern = DATE_TIME_FORMAT)
                                                 LocalDateTime rangeEnd,
                                             @RequestParam(defaultValue = "false") boolean onlyAvailable,
                                             @RequestParam(required = false) String sort,
                                             @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                             @Positive @RequestParam(defaultValue = "10") int size,
                                             HttpServletRequest request) {
        String url = request.getRequestURI();
        String ip = request.getRemoteAddr();

        return eventService.getPublicEventsByFilters(text, categories, paid, rangeStart,
                rangeEnd, onlyAvailable, sort, from, size, url, ip);
    }
}
