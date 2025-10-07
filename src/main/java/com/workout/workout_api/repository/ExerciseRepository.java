package com.workout.workout_api.repository;

import com.workout.workout_api.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByCategory(String category);
    List<Exercise> findByName(String name);
    boolean existsByName(String name);
}
