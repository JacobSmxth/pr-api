package com.workout.workout_api.controller;

import com.workout.workout_api.entity.Exercise;
import com.workout.workout_api.entity.ExerciseSet;
import com.workout.workout_api.entity.Session;
import com.workout.workout_api.repository.ExerciseRepository;
import com.workout.workout_api.repository.ExerciseSetRepository;
import com.workout.workout_api.repository.SessionRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ExerciseSetRepository exerciseSetRepository;

    @GetMapping
    public List<Exercise> getAllExercises(@RequestParam(required = false) String category) {
        if (category != null) {
            return exerciseRepository.findByCategory(category.toUpperCase());
        }
        return exerciseRepository.findAll();
    }

    @GetMapping("/session")
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        return exerciseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sets/{id}")
    public ResponseEntity<ExerciseSet> getSetById(@PathVariable Long id) {
        return exerciseSetRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/session")
    public ResponseEntity<Session> createSession(@RequestBody Session session) {
        Session savedSession = sessionRepository.save(session);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSession);
    }

    @PostMapping("/session/{id}")
    public ResponseEntity<Exercise> addExerciseToSession(@PathVariable Long id, @RequestBody Exercise exercise) {
       return sessionRepository.findById(id)
               .map(session -> {
                   exercise.setSession(session);
                   for(ExerciseSet set : exercise.getSets()) {
                       set.setExercise(exercise);
                   }

                   Exercise savedExercise = exerciseRepository.save(exercise);
                   session.getExercises().add(savedExercise);

                   return ResponseEntity.status(HttpStatus.CREATED).body(savedExercise);
               })
               .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
        if (exerciseRepository.existsByName(exercise.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        for (ExerciseSet set : exercise.getSets()) {
            set.setExercise(exercise);
        }

        Exercise savedExercise = exerciseRepository.save(exercise);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExercise);
    }

}
