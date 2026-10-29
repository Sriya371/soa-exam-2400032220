package com.klef.soa.exam.service;

import java.util.List;
import com.klef.soa.exam.dto.NutritionPlanRequest;
import com.klef.soa.exam.dto.NutritionPlanResponse;

public interface NutritionPlanService {
    NutritionPlanResponse addNutritionPlan(NutritionPlanRequest request);
    List<NutritionPlanResponse> displayAllNutritionPlan();
    NutritionPlanResponse getNutritionPlanById(Long id);
    NutritionPlanResponse updateNutritionPlan(Long id, NutritionPlanRequest request);
    void deleteNutritionPlanById(Long id);
}
