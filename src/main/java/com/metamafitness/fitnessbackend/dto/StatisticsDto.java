package com.metamafitness.fitnessbackend.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticsDto {
    public BigDecimal totalProfit;
    public Long newFeedbacks;
    public Long newSales;
    public Long newUsers;
}
