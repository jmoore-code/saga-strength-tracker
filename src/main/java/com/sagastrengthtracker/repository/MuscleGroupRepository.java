package com.sagastrengthtracker.repository;

import com.sagastrengthtracker.model.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, Long> {

    // Find by name
    Optional<MuscleGroup> findByNameIgnoreCase(String name);

    // Check if muscle group exists
    boolean existsByNameIgnoreCase(String name);

    // Find muscle groups for an exercise
    @Query("SELECT muscleGroup FROM MuscleGroup muscleGroup " +
            "JOIN muscleGroup.exercises exercise WHERE exercise.id = :exerciseId")
    List<MuscleGroup> findByExerciseId(@Param("exerciseId") Long exerciseId);

    // Find all muscle groups ordered by name
    List<MuscleGroup> findAllByOrderByNameAsc();
}