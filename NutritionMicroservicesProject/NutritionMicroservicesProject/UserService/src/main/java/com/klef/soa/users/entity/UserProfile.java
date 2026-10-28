package com.klef.soa.users.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity @Table(name="user_profiles") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserProfile {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotBlank private String fullName;
    @NotBlank @Email private String email;
    @NotNull @Positive private Integer age;
    @NotNull @Positive private Double heightCm;
    @NotNull @Positive private Double weightKg;
}
