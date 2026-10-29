package com.klef.soa.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.klef.soa.exam.entity.NutritionPlanEntity;

public interface NutritionPlanRepository extends JpaRepository<NutritionPlanEntity, Long> {
}
