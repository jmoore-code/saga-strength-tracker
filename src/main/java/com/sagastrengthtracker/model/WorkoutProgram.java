package com.sagastrengthtracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "workout_programs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel;

    private Integer estimatedWeeks;

    private String category;

    private boolean isPublic = false;

    // Soft delete flag
    private boolean deleted = false;

    // Audit fields
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "workoutProgram", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dayNumber ASC")
    private List<WorkoutDay> workoutDays = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "program_tags",
            joinColumns = @JoinColumn(name = "program_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    // Methods to maintain relationship consistency
    public void addWorkoutDay(WorkoutDay workoutDay) {
        workoutDays.add(workoutDay);
        workoutDay.setWorkoutProgram(this);
    }

    public void removeWorkoutDay(WorkoutDay workoutDay) {
        workoutDays.remove(workoutDay);
        workoutDay.setWorkoutProgram(null);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getWorkoutPrograms().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getWorkoutPrograms().remove(this);
    }

    // Enum for difficulty level
    public enum DifficultyLevel {
        BEGINNER,
        INTERMEDIATE,
        ADVANCED,
        EXPERT
    }
}