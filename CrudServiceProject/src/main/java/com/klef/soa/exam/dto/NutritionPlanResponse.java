package com.klef.soa.exam.dto;

import jakarta.validation.constraints.NotBlank;

public class NutritionPlanResponse
{
	    private Long id;
	    private String planName;
	    private Integer dailyCalories;
	    private double weeklyCost;
	    private boolean active;
}
