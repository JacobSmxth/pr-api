package com.workout.workout_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String name;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<Exercise> exercises = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        date = LocalDate.now();
    }

    public Session() {}
    public Session(LocalDate date, String name, List<Exercise> exercises) {
        this.name = name;
        this.exercises = exercises;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
