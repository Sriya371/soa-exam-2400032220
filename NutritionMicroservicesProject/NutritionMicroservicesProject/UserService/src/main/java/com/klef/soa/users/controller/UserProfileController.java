package com.klef.soa.users.controller;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.klef.soa.users.entity.UserProfile;
import com.klef.soa.users.service.UserProfileService;
@RestController @RequestMapping("/users") @RequiredArgsConstructor
public class UserProfileController {
 private final UserProfileService service;
 @GetMapping("/") public String health() { return "UserProfile is running"; }
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public UserProfile create(@Valid @RequestBody UserProfile value) { return service.create(value); }
 @GetMapping public List<UserProfile> findAll() { return service.findAll(); }
 @GetMapping("/{id}") public UserProfile findById(@PathVariable Long id) { return service.findById(id); }
 @PutMapping("/{id}") public UserProfile update(@PathVariable Long id, @Valid @RequestBody UserProfile value) { return service.update(id,value); }
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
