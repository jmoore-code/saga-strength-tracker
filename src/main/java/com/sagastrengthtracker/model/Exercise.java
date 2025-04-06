package com.sagastrengthtracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "exercises")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private ExerciseCategory category;

    private Boolean isCompound = false;

    private String equipmentRequired;

    private Boolean isCustom = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    // Soft delete flag
    private boolean deleted = false;

    // Audit fields
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "exercise_muscle_groups",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_group_id")
    )
    private Set<com.sagastrengthtracker.model.MuscleGroup> muscleGroups = new HashSet<>();

    @OneToMany(mappedBy = "exercise")
    private Set<WorkoutExercise> workoutExercises = new HashSet<>();

    // Methods to maintain relationship consistency
    public void addMuscleGroup(com.sagastrengthtracker.model.MuscleGroup muscleGroup) {
        muscleGroups.add(muscleGroup);
        muscleGroup.getExercises().add(this);
    }

    public void removeMuscleGroup(com.sagastrengthtracker.model.MuscleGroup muscleGroup) {
        muscleGroups.remove(muscleGroup);
        muscleGroup.getExercises().remove(this);
    }

    // Exercise Categories
    public enum ExerciseCategory {
        STRENGTH,
        CARDIO,
        FLEXIBILITY,
        BALANCE,
        PLYOMETRIC,
        FUNCTIONAL
    }
}