package com.klef.soa.food.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.klef.soa.food.entity.FoodItem;
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {}
