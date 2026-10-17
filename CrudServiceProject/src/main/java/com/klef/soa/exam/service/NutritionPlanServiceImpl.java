package com.klef.soa.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.soa.exam.entity.NutritionPlanEntity;
import com.klef.soa.exam.entity.NutritionPlanEntity.NutritionPlanEntityBuilder;
import com.klef.soa.exam.repository.NutritrionPlanRepository;

@Service
public class NutritionPlanServiceImpl  implements NutritionPlanService{
	@Autowired
    private NutritrionPlanRepository nutitionRepository;

	@Override
	public NutritionPlanEntity addNutritionPlan(NutritionPlanEntity nutritionplan) 
	{
	return  nutitionRepository.save(nutritionplan);
			
	}

	@Override
	public List<NutritionPlanEntity> displayAllNutritionPlan() {
		return nutitionRepository.findAll();
	}

	@Override
	public String deleteNutritionPlanById(Long id) {
		// TODO Auto-generated method stub
		boolean status =nutitionRepository.existsById(id);
		if(status) 
		{
			nutitionRepository.deleteById(id);
			return " NutritionPlanId Deleted Successfully";
		}
		else 
		{
			return "NutritionPlanId ID Not Found";
		}
	}
}

