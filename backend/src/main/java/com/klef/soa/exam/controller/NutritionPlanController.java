package com.klef.soa.exam.controller;

import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.klef.soa.exam.dto.NutritionPlanRequest;
import com.klef.soa.exam.dto.NutritionPlanResponse;
import com.klef.soa.exam.service.NutritionPlanService;

@RestController
@RequestMapping("/nutritionplan")
@RequiredArgsConstructor
public class NutritionPlanController {
    private final NutritionPlanService service;

    @GetMapping("/") public String test() { return "Nutrition Plan Service is running"; }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public NutritionPlanResponse add(@Valid @RequestBody NutritionPlanRequest request) {
        return service.addNutritionPlan(request);
    }

    @GetMapping("/displayall")
    public List<NutritionPlanResponse> displayAll() { return service.displayAllNutritionPlan(); }

    @GetMapping("/{id}")
    public NutritionPlanResponse getById(@PathVariable Long id) { return service.getNutritionPlanById(id); }

    @PutMapping("/update/{id}")
    public NutritionPlanResponse update(@PathVariable Long id, @Valid @RequestBody NutritionPlanRequest request) {
        return service.updateNutritionPlan(id, request);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.deleteNutritionPlanById(id); }
}
