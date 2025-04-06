package com.sagastrengthtracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "exercise_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_session_id", nullable = false)
    private com.sagastrengthtracker.model.WorkoutSession workoutSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    private Integer setNumber;

    private Integer reps;

    private Double weight;

    private Boolean completed = true;

    // Used to track if this was a warmup, working, or drop set
    @Enumerated(EnumType.STRING)
    private SetType setType = SetType.WORKING;

    private LocalDateTime completedAt;

    @Column(columnDefinition = "TEXT")
    private String notes;

    // Set types
    public enum SetType {
        WARMUP,
        WORKING,
        DROP,
        FAILURE
    }
}