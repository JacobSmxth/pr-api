package com.workout.workout_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name cannot be blank")
    private String name;

    private String category;

    @NotNull(message = "Weight is required")
    @Min(value = 1, message = "Weight must be greater than 0")
    private Integer weight;

    @NotNull(message = "Reps is required")
    @Min(value = 1, message = "Reps must be greater than 0")
    private Integer reps;

    public Exercise() {}

    public Exercise(String name, String category, Integer weight, Integer reps) {
        this.name = name;
        this.category = category;
        this.weight = weight;
        this.reps = reps;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category.toUpperCase();
    }

    public Integer getWeight() {
        return weight;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getReps() {
        return reps;
    }
    public void setReps(Integer reps) {
        this.reps = reps;
    }

}
