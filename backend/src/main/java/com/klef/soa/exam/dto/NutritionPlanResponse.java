package com.klef.soa.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NutritionPlanResponse {
    private Long id;
    private String planName;
    private Integer dailyCalories;
    private double weeklyCost;
    private boolean active;
}
