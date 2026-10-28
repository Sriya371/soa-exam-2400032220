package com.klef.soa.food.controller;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.klef.soa.food.entity.FoodItem;
import com.klef.soa.food.service.FoodItemService;
@RestController 
@RequestMapping("/foods") 
@RequiredArgsConstructor
public class FoodItemController {
 private final FoodItemService service;
 
 @GetMapping("/") 
 public String health() { 
	 return "FoodItem is running"; 
	 }
 @PostMapping 
 @ResponseStatus(HttpStatus.CREATED) public FoodItem create(@Valid @RequestBody FoodItem value) { return service.create(value); }
 @GetMapping public List<FoodItem> findAll() { return service.findAll(); }
 @GetMapping("/{id}") public FoodItem findById(@PathVariable Long id) { return service.findById(id); }
 @PutMapping("/{id}") public FoodItem update(@PathVariable Long id, @Valid @RequestBody FoodItem value) { return service.update(id,value); }
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
