package com.metamafitness.fitnessbackend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDto {

    private String title;
    private String details;

    private LocalDateTime createdAt;

    private LocalDateTime consumedAt;
}
