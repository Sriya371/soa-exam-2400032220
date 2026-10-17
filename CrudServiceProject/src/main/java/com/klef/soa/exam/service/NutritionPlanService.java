package com.klef.soa.exam.service;

import java.util.List;

import com.klef.soa.exam.entity.NutritionPlanEntity;

public interface NutritionPlanService 
{
   NutritionPlanEntity addNutritionPlan(NutritionPlanEntity nutritionplan);
   List<NutritionPlanEntity> displayAllNutritionPlan();
   String deleteNutritionPlanById(Long id);
}
