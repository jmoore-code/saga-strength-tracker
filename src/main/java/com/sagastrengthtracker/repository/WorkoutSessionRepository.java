package com.sagastrengthtracker.repository;

import com.sagastrengthtracker.model.WorkoutSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long> {

    // Find user's workout sessions, ordered by start time descending
    Page<WorkoutSession> findByUserIdOrderByStartTimeDesc(Long userId, Pageable pageable);

    // Find workout sessions for a specific workout day
    List<WorkoutSession> findByWorkoutDayId(Long workoutDayId);

    // Find workout sessions in a date range
    @Query("SELECT workoutSession FROM WorkoutSession workoutSession " +
            "WHERE workoutSession.user.id = :userId " +
            "AND workoutSession.startTime >= :startDate AND workoutSession.startTime <= :endDate " +
            "ORDER BY workoutSession.startTime DESC")
    List<WorkoutSession> findByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    // Count workouts per program
    @Query("SELECT workoutSession.workoutDay.workoutProgram.id, COUNT(workoutSession) " +
            "FROM WorkoutSession workoutSession " +
            "WHERE workoutSession.user.id = :userId " +
            "GROUP BY workoutSession.workoutDay.workoutProgram.id")
    List<Object[]> countWorkoutsByProgram(@Param("userId") Long userId);

    // Get recent workout sessions
    @Query("SELECT workoutSession FROM WorkoutSession workoutSession " +
            "WHERE workoutSession.user.id = :userId " +
            "ORDER BY workoutSession.startTime DESC")
    List<WorkoutSession> findRecentSessions(@Param("userId") Long userId, Pageable pageable);

    // Find incomplete sessions (missing end time)
    List<WorkoutSession> findByUserIdAndEndTimeIsNull(Long userId);

    // Count total workouts for a user
    long countByUserId(Long userId);

    // Average workout duration for a user
    @Query("SELECT AVG(workoutSession.durationSeconds) FROM WorkoutSession workoutSession " +
            "WHERE workoutSession.user.id = :userId AND workoutSession.durationSeconds IS NOT NULL")
    Double averageWorkoutDuration(@Param("userId") Long userId);
}