package com.klef.soa.exam.entity;

import org.springframework.aot.generate.GeneratedTypeReference;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="NutritionPlan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class NutritionPlanEntity 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String planName;

    @Column(nullable = false)
    private Integer dailyCalories;

    @Column(nullable = false)
    private double weeklyCost;

    @Column(nullable = false)
    private boolean active;


}
