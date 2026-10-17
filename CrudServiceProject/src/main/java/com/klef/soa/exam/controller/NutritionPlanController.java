package com.klef.soa.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.klef.soa.exam.entity.NutritionPlanEntity;
import com.klef.soa.exam.service.NutritionPlanService;

@RestController
@RequestMapping("/nutritionplan")
public class NutritionPlanController {

	
    @Autowired
    private NutritionPlanService nutritionPlanService;
    @GetMapping("/")
    public String test()
    {
     return "Crud service is running";
    }


    @PostMapping("/add")
    public NutritionPlanEntity addNutritionPlan(
            @RequestBody NutritionPlanEntity nutritionplan) {
        return nutritionPlanService.addNutritionPlan(nutritionplan);
    }

    
    @GetMapping("/displayall")
    public List<NutritionPlanEntity> displayAllNutritionPlan() {
        return nutritionPlanService.displayAllNutritionPlan();
    }

   
    @DeleteMapping("/delete/{id}")
    public String deleteNutritionPlanById(@PathVariable Long id) {
        return nutritionPlanService.deleteNutritionPlanById(id);
    }
}