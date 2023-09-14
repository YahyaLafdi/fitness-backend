package com.metamafitness.fitnessbackend.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto implements Serializable {
    private String status;
    private String id;
    private String redirectUrl;
    private String createdAt;
}
