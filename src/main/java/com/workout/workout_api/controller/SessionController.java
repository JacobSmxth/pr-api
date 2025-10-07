package com.workout.workout_api.controller;

import com.workout.workout_api.entity.Exercise;
import com.workout.workout_api.entity.ExerciseSet;
import com.workout.workout_api.entity.Session;
import com.workout.workout_api.exception.ResourceNotFoundException;
import com.workout.workout_api.repository.ExerciseRepository;
import com.workout.workout_api.repository.SessionRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @GetMapping
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Session getSessionById(@PathVariable Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session", id));
    }

    @PostMapping
    public ResponseEntity<Session> createSession(@RequestBody Session session) {
        Session savedSession = sessionRepository.save(session);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSession);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Exercise> addExerciseToSession(@PathVariable Long id, @RequestBody Exercise exercise) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session", id));

        exercise.setSession(session);
        for(ExerciseSet set: exercise.getSets()) {
            set.setExercise(exercise);
        }
        Exercise savedExercise = exerciseRepository.save(exercise);
        session.getExercises().add(savedExercise);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedExercise);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Session> replaceSession(@PathVariable Long id, @RequestBody Session session) {
        Session oldSession = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session", id));
        oldSession.setName(session.getName());
        Session updated = sessionRepository.save(oldSession);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSessionById(@PathVariable Long id) {
       Session session = sessionRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Session", id));
       sessionRepository.delete(session);
       return ResponseEntity.noContent().build();
    }
}
