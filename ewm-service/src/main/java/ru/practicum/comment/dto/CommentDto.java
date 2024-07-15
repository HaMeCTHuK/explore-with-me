package ru.practicum.comment.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {
    private Long id;
    private String text;
    private Long commentatorId;
    private Long eventId;
    private LocalDateTime created;
    private LocalDateTime updated;
}
