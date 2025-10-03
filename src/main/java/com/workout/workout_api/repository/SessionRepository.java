package com.workout.workout_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.workout.workout_api.entity.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
