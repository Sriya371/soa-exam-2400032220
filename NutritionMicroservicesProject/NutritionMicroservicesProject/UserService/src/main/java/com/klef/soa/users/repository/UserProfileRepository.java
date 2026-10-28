package com.klef.soa.users.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.klef.soa.users.entity.UserProfile;
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {}
