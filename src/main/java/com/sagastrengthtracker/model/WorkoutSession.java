package com.sagastrengthtracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workout_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_day_id")
    private com.sagastrengthtracker.model.WorkoutDay workoutDay;

    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    // Duration in seconds
    private Long durationSeconds;

    private Integer perceivedExertion; // Scale 1-10

    @Column(columnDefinition = "TEXT")
    private String notes;

    // Audit fields
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "workoutSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseLog> exerciseLogs = new ArrayList<>();

    // Helper methods
    public void addExerciseLog(ExerciseLog exerciseLog) {
        exerciseLogs.add(exerciseLog);
        exerciseLog.setWorkoutSession(this);
    }

    public void removeExerciseLog(ExerciseLog exerciseLog) {
        exerciseLogs.remove(exerciseLog);
        exerciseLog.setWorkoutSession(null);
    }

    // Calculate duration when ending a workout
    public void endWorkout() {
        this.endTime = LocalDateTime.now();
        this.durationSeconds = Duration.between(this.startTime, this.endTime).getSeconds();
    }
}