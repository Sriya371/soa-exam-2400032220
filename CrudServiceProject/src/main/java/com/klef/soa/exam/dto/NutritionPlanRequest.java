package com.klef.soa.exam.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class NutritionPlanRequest {

	@NotBlank
    private String planName;

    @NotBlank
    private Integer dailyCalories;

    @NotBlank
    private double weeklyCost;

   @NotBlank
    private boolean active;
}
