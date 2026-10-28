package com.klef.soa.users.service;
import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.klef.soa.users.entity.UserProfile;
import com.klef.soa.users.repository.UserProfileRepository;
@Service @RequiredArgsConstructor
public class UserProfileService {
 private final UserProfileRepository repository;
 public UserProfile create(UserProfile value) { value.setId(null); return repository.save(value); }
 public List<UserProfile> findAll() { return repository.findAll(); }
 public UserProfile findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found: " + id)); }
 public UserProfile update(Long id, UserProfile value) { UserProfile old=findById(id);
 old.setFullName(value.getFullName());
 old.setEmail(value.getEmail());
 old.setAge(value.getAge());
 old.setHeightCm(value.getHeightCm());
 old.setWeightKg(value.getWeightKg());
 return repository.save(old); }
 public void delete(Long id) { repository.delete(findById(id)); }
}
