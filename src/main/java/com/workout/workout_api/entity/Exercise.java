package com.workout.workout_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name cannot be blank")
    private String name;

    private String category;

    @ManyToOne
    @JoinColumn(name = "session_id")
    @JsonIgnore
    private Session session;


    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    private List<ExerciseSet> sets = new ArrayList<>();


    public Exercise() {}

    public Exercise(String name, String category) {
        this.name = name;
        this.category = category;
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

    public Session getSession() {
        return session;
    }
    public void setSession(Session session) {
        this.session = session;
    }

    public List<ExerciseSet> getSets() {
        return sets;
    }
    public void setSets(List<ExerciseSet> sets) {
        this.sets = sets;
    }
}
