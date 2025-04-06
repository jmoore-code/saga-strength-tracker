package com.sagastrengthtracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workout_days")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer dayNumber;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private Boolean isRestDay = false;

    // Audit fields
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private com.sagastrengthtracker.model.WorkoutProgram workoutProgram;

    @OneToMany(mappedBy = "workoutDay", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("exerciseOrder ASC")
    private List<WorkoutExercise> workoutExercises = new ArrayList<>();

    @OneToMany(mappedBy = "workoutDay")
    private List<com.sagastrengthtracker.model.WorkoutSession> workoutSessions = new ArrayList<>();

    // Methods to maintain relationship consistency
    public void addWorkoutExercise(WorkoutExercise workoutExercise) {
        workoutExercises.add(workoutExercise);
        workoutExercise.setWorkoutDay(this);
    }

    public void removeWorkoutExercise(WorkoutExercise workoutExercise) {
        workoutExercises.remove(workoutExercise);
        workoutExercise.setWorkoutDay(null);
    }
}