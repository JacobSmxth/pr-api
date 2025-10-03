package com.workout.workout_api.controller;

import com.workout.workout_api.entity.Exercise;
import com.workout.workout_api.repository.ExerciseRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @GetMapping
    public List<Exercise> getAllExercises(@RequestParam(required = false) String category) {
        if (category != null) {
            return exerciseRepository.findByCategory(category.toUpperCase());
        }
        return exerciseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        return exerciseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
        if (exerciseRepository.existsByName(exercise.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Exercise savedExercise = exerciseRepository.save(exercise);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExercise);
    }

    @GetMapping("/{id}/max")
    public ResponseEntity<Long> getOneRepMax(@PathVariable Long id) {
        return exerciseRepository.findById(id)
                .map(e -> {
                    long oneRepMax = Math.round(e.getWeight().doubleValue() * (36.0 / (37.0 - e.getReps())));
                    return ResponseEntity.ok(oneRepMax);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
