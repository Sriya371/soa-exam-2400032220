package com.klef.soa.food.service;
import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.klef.soa.food.entity.FoodItem;
import com.klef.soa.food.repository.FoodItemRepository;
@Service @RequiredArgsConstructor
public class FoodItemService {
 private final FoodItemRepository repository;
 public FoodItem create(FoodItem value) { value.setId(null); return repository.save(value); }
 public List<FoodItem> findAll() { return repository.findAll(); }
 public FoodItem findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found: " + id)); }
 public FoodItem update(Long id, FoodItem value) { FoodItem old=findById(id);
 old.setName(value.getName());
 old.setCategory(value.getCategory());
 old.setCaloriesPerServing(value.getCaloriesPerServing());
 old.setPrice(value.getPrice());
 old.setAvailable(value.getAvailable());
 return repository.save(old); }
 public void delete(Long id) { repository.delete(findById(id)); }
}
