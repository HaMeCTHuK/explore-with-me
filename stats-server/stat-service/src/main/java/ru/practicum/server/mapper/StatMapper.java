package ru.practicum.server.mapper;

import ru.practicum.dto.StatDto;
import ru.practicum.server.model.StatEntity;

public class StatMapper {
    public static StatDto toStatDto(StatEntity statEntity) {
        return StatDto.builder()
                .app(statEntity.getApp())
                .uri(statEntity.getUri())
                .hits(statEntity.getHits())
                .build();
    }
}
