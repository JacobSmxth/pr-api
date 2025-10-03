package com.workout.workout_api.repository;

import com.workout.workout_api.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByCategory(String category);
    boolean existsByName(String name);
}
