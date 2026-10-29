package com.klef.soa.exam.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class NutritionPlanRequest {
    @NotBlank(message = "Plan name is required")
    private String planName;

    @Min(value = 1, message = "Daily calories must be greater than zero")
    private Integer dailyCalories;

    @PositiveOrZero(message = "Weekly cost cannot be negative")
    private double weeklyCost;

    private boolean active = true;
}
