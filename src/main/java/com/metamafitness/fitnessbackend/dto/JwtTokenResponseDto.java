package com.metamafitness.fitnessbackend.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtTokenResponseDto {
    private JwtToken accessToken;
    private JwtToken refreshToken;
}