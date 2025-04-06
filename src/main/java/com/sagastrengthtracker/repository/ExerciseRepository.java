package com.sagastrengthtracker.repository;

import com.sagastrengthtracker.model.Exercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    // Find non-deleted exercises
    List<Exercise> findByDeletedFalse();

    // Paginated query for browsing exercises
    Page<Exercise> findByDeletedFalse(Pageable pageable);

    // Find by name (case insensitive)
    Optional<Exercise> findByNameIgnoreCaseAndDeletedFalse(String name);

    // Find by category
    List<Exercise> findByCategoryAndDeletedFalse(Exercise.ExerciseCategory category);

    // Find exercises that target specific muscle group
    @Query("SELECT exercise FROM Exercise exercise JOIN exercise.muscleGroups muscleGroup " +
            "WHERE muscleGroup.id = :muscleGroupId AND exercise.deleted = false")
    List<Exercise> findByMuscleGroupId(@Param("muscleGroupId") Long muscleGroupId);

    // Find by equipment
    List<Exercise> findByEquipmentRequiredAndDeletedFalse(String equipmentRequired);

    // Find compound exercises
    List<Exercise> findByIsCompoundTrueAndDeletedFalse();

    // Search exercises by name or description
    @Query("SELECT exercise FROM Exercise exercise WHERE exercise.deleted = false AND " +
            "(LOWER(exercise.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(exercise.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Exercise> searchExercises(@Param("searchTerm") String searchTerm, Pageable pageable);

    // Find custom exercises created by a user
    List<Exercise> findByCreatedByIdAndIsCustomTrueAndDeletedFalse(Long userId);

    // Check if exercise name already exists
    boolean existsByNameIgnoreCaseAndDeletedFalse(String name);
}