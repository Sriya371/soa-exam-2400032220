package com.klef.soa.exam.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import lombok.RequiredArgsConstructor;
import com.klef.soa.exam.dto.NutritionPlanRequest;
import com.klef.soa.exam.dto.NutritionPlanResponse;
import com.klef.soa.exam.entity.NutritionPlanEntity;
import com.klef.soa.exam.repository.NutritionPlanRepository;

@Service
@RequiredArgsConstructor
public class NutritionPlanServiceImpl implements NutritionPlanService {
    private final NutritionPlanRepository repository;

    @Override public NutritionPlanResponse addNutritionPlan(NutritionPlanRequest request) {
        NutritionPlanEntity entity = NutritionPlanEntity.builder()
            .planName(request.getPlanName()).dailyCalories(request.getDailyCalories())
            .weeklyCost(request.getWeeklyCost()).active(request.isActive()).build();
        return toResponse(repository.save(entity));
    }

    @Override public List<NutritionPlanResponse> displayAllNutritionPlan() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override public NutritionPlanResponse getNutritionPlanById(Long id) {
        return toResponse(findPlan(id));
    }

    @Override public NutritionPlanResponse updateNutritionPlan(Long id, NutritionPlanRequest request) {
        NutritionPlanEntity entity = findPlan(id);
        entity.setPlanName(request.getPlanName());
        entity.setDailyCalories(request.getDailyCalories());
        entity.setWeeklyCost(request.getWeeklyCost());
        entity.setActive(request.isActive());
        return toResponse(repository.save(entity));
    }

    @Override public void deleteNutritionPlanById(Long id) {
        NutritionPlanEntity entity = findPlan(id);
        repository.delete(entity);
    }

    private NutritionPlanEntity findPlan(Long id) {
        return repository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Nutrition plan not found: " + id));
    }

    private NutritionPlanResponse toResponse(NutritionPlanEntity entity) {
        return NutritionPlanResponse.builder().id(entity.getId()).planName(entity.getPlanName())
            .dailyCalories(entity.getDailyCalories()).weeklyCost(entity.getWeeklyCost())
            .active(entity.isActive()).build();
    }
}
