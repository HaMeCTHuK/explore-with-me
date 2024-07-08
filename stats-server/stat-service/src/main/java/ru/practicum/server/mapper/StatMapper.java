package ru.practicum.server.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.StatDto;
import ru.practicum.server.model.StatEntity;

@UtilityClass
public class StatMapper {
    public static StatDto toStatDto(StatEntity statEntity) {
        return StatDto.builder()
                .app(statEntity.getApp())
                .uri(statEntity.getUri())
                .hits(statEntity.getHits())
                .build();
    }
}
