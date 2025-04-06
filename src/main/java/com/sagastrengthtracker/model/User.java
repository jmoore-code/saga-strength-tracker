package com.sagastrengthtracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String displayName;

    // Profile information
    private Double weight;
    private Double height;
    private String fitnessGoals;

    // Subscription information
    private boolean isPremium = false;
    private LocalDate subscriptionExpiryDate;

    // Audit fields
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Soft delete functionality
    private boolean deleted = false;

    // Relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<com.sagastrengthtracker.model.WorkoutProgram> workoutPrograms = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<com.sagastrengthtracker.model.WorkoutSession> workoutSessions = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    // Helper methods for bidirectional relationship management
    public void addWorkoutProgram(com.sagastrengthtracker.model.WorkoutProgram workoutProgram) {
        workoutPrograms.add(workoutProgram);
        workoutProgram.setUser(this);
    }

    public void removeWorkoutProgram(com.sagastrengthtracker.model.WorkoutProgram workoutProgram) {
        workoutPrograms.remove(workoutProgram);
        workoutProgram.setUser(null);
    }

    public void addWorkoutSession(com.sagastrengthtracker.model.WorkoutSession workoutSession) {
        workoutSessions.add(workoutSession);
        workoutSession.setUser(this);
    }

    public void removeWorkoutSession(com.sagastrengthtracker.model.WorkoutSession workoutSession) {
        workoutSessions.remove(workoutSession);
        workoutSession.setUser(null);
    }
}