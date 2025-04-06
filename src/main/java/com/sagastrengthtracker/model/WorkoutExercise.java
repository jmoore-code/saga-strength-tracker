package com.sagastrengthtracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workout_exercises")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_day_id", nullable = false)
    private com.sagastrengthtracker.model.WorkoutDay workoutDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    private Integer exerciseOrder;

    private Integer targetSets;

    private Integer targetReps;

    // Can be null if exercise doesn't use weight (e.g., bodyweight exercises)
    private Double targetWeight;

    // Rest time in seconds
    private Integer restTime;

    @Column(columnDefinition = "TEXT")
    private String notes;
}