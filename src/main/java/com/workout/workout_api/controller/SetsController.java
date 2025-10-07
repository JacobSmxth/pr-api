package com.workout.workout_api.controller;

import com.workout.workout_api.entity.ExerciseSet;
import com.workout.workout_api.exception.ResourceNotFoundException;
import com.workout.workout_api.repository.ExerciseSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sets")
public class SetsController {

    @Autowired
    private ExerciseSetRepository exerciseSetRepository;

    @GetMapping
    public List<ExerciseSet> getAllSets(@RequestParam(required = false) Integer threshold) {
        if (threshold != null) {
            return exerciseSetRepository.findByWeightGreaterThan(threshold);
        }
        return exerciseSetRepository.findAll();
    }

    @GetMapping("/{id}")
    public ExerciseSet getSetById(@PathVariable Long id) {
        return exerciseSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Set", id));
    }

    @GetMapping("/{id}/max")
    public Double getMaxOffSet(@PathVariable Long id) {
        ExerciseSet set = exerciseSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Set", id));
        return Math.floor(set.getWeight() * (1 + (set.getReps()/30.0)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExerciseSet> replaceSetById(@PathVariable Long id, @RequestBody ExerciseSet exerciseSetDetails) {
        ExerciseSet set = exerciseSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Set", id));
        set.setReps(exerciseSetDetails.getReps());
        set.setWeight(exerciseSetDetails.getWeight());
        set.setSetNumber(exerciseSetDetails.getSetNumber());
        ExerciseSet updated = exerciseSetRepository.save(set);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeSetById(@PathVariable Long id) {
        ExerciseSet set = exerciseSetRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Sets", id));
        exerciseSetRepository.delete(set);
        return ResponseEntity.noContent().build();
    }


}
