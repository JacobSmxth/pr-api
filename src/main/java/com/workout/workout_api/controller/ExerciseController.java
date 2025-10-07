package com.workout.workout_api.controller;

import com.workout.workout_api.entity.Exercise;
import com.workout.workout_api.entity.ExerciseSet;
import com.workout.workout_api.exception.DuplicateResourceException;
import com.workout.workout_api.exception.ResourceNotFoundException;
import com.workout.workout_api.repository.ExerciseRepository;
import com.workout.workout_api.repository.SessionRepository;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<Exercise>> getAllExercises(@RequestParam(required = false) String category,@RequestParam(required = false) String name) {
        if (category != null) {
            return ResponseEntity.ok(exerciseRepository.findByCategory(category.toUpperCase()));
        }
        if (name != null) {
            return ResponseEntity.ok(exerciseRepository.findByName(name));
        }

        return ResponseEntity.ok(exerciseRepository.findAll());
    }

    @GetMapping("/{id}")
    public Exercise getExerciseById(@PathVariable Long id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", id));
    }

    @PostMapping
    public ResponseEntity<Exercise> createExercise(@Valid @RequestBody Exercise exercise) {
        if (exerciseRepository.existsByName(exercise.getName())) {
            throw new DuplicateResourceException("Exercise with name '" + exercise.getName() + "' already exists.");
        }
        for (ExerciseSet set : exercise.getSets()) {
            set.setExercise(exercise);
        }

        Exercise savedExercise = exerciseRepository.save(exercise);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExercise);
    }

}
