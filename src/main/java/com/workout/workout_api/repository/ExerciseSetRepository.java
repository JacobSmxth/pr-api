package com.workout.workout_api.repository;

import com.workout.workout_api.entity.ExerciseSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {
    List<ExerciseSet> findByWeightGreaterThan(Integer threshold);
}
