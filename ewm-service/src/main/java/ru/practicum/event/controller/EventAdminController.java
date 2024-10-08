package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.enumerations.EventState;
import ru.practicum.event.dto.EventDto;
import ru.practicum.event.dto.EventDtoForAdminUpdate;
import ru.practicum.event.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.util.DateConstants.DATE_TIME_FORMAT;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class EventAdminController {
    private final EventService eventService;

    @PatchMapping("/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto updateEventByAdmin(@PathVariable final Long eventId,
                                       @RequestBody @Valid EventDtoForAdminUpdate dtoForAdminUpdate) {
        return eventService.updateEventByAdmin(eventId, dtoForAdminUpdate);
    }

    @GetMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    public Collection<EventDto> searchEvents(@RequestParam(defaultValue = "") List<Long> users,
                                             @RequestParam(defaultValue = "") List<String> states,
                                             @RequestParam(defaultValue = "") List<Long> categories,
                                             @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_FORMAT)
                                             LocalDateTime rangeStart,
                                             @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_FORMAT)
                                             LocalDateTime rangeEnd,
                                             @RequestParam(value = "from", defaultValue = "0")
                                             @PositiveOrZero(message = "Значение 'from' должно быть неотрицательным") Integer from,
                                             @RequestParam(value = "size", defaultValue = "10")
                                             @Positive(message = "Значение 'size' должно быть положительным") Integer size) {

        return eventService.searchEvents(users, states.stream().map(EventState::valueOf).collect(Collectors.toList()),
                categories, rangeStart, rangeEnd, from, size);
    }
}
