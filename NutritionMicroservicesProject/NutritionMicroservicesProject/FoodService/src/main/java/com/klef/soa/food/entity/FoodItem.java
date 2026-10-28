package com.klef.soa.food.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity @Table(name="food_items") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class FoodItem {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotBlank private String name;
    @NotBlank private String category;
    @NotNull @PositiveOrZero private Integer caloriesPerServing;
    @NotNull @PositiveOrZero private Double price;
    @NotNull private Boolean available;
}
