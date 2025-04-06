package com.sagastrengthtracker.repository;

import com.sagastrengthtracker.model.WorkoutProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutProgramRepository extends JpaRepository<WorkoutProgram, Long> {

    // Find all programs created by a specific user
    List<WorkoutProgram> findByUserIdAndDeletedFalse(Long userId);

    // Find public programs (for browsing)
    Page<WorkoutProgram> findByIsPublicTrueAndDeletedFalse(Pageable pageable);

    // Search programs by name or description
    @Query("SELECT workoutProgram FROM WorkoutProgram workoutProgram WHERE workoutProgram.deleted = false AND " +
            "(workoutProgram.isPublic = true OR workoutProgram.user.id = :userId) AND " +
            "(LOWER(workoutProgram.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(workoutProgram.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<WorkoutProgram> searchPrograms(@Param("searchTerm") String searchTerm,
                                        @Param("userId") Long userId,
                                        Pageable pageable);

    // Find programs by difficulty level
    @Query("SELECT workoutProgram FROM WorkoutProgram workoutProgram WHERE workoutProgram.deleted = false AND " +
            "(workoutProgram.isPublic = true OR workoutProgram.user.id = :userId) AND " +
            "workoutProgram.difficultyLevel = :difficultyLevel")
    Page<WorkoutProgram> findByDifficultyLevel(@Param("difficultyLevel") WorkoutProgram.DifficultyLevel difficultyLevel,
                                               @Param("userId") Long userId,
                                               Pageable pageable);

    // Find programs by category
    @Query("SELECT workoutProgram FROM WorkoutProgram workoutProgram WHERE workoutProgram.deleted = false AND " +
            "(workoutProgram.isPublic = true OR workoutProgram.user.id = :userId) AND " +
            "workoutProgram.category = :category")
    Page<WorkoutProgram> findByCategory(@Param("category") String category,
                                        @Param("userId") Long userId,
                                        Pageable pageable);

    // Count how many programs a user has (for free tier limitations)
    long countByUserIdAndDeletedFalse(Long userId);
}