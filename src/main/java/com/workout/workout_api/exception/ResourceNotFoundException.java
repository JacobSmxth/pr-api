package com.workout.workout_api.exception;

import jakarta.annotation.Resource;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " not found found with id: " + id);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
