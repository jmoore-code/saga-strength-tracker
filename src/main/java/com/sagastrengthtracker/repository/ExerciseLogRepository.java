package com.sagastrengthtracker.repository;

import com.sagastrengthtracker.model.ExerciseLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExerciseLogRepository extends JpaRepository<ExerciseLog, Long> {

    // Find logs for a specific workout session
    List<ExerciseLog> findByWorkoutSessionIdOrderByExerciseIdAscSetNumberAsc(Long sessionId);

    // Find logs for a specific exercise across all sessions for a user
    @Query("SELECT exerciseLog FROM ExerciseLog exerciseLog " +
            "JOIN exerciseLog.workoutSession workoutSession " +
            "WHERE workoutSession.user.id = :userId AND exerciseLog.exercise.id = :exerciseId " +
            "ORDER BY workoutSession.startTime DESC, exerciseLog.setNumber ASC")
    List<ExerciseLog> findByUserIdAndExerciseId(
            @Param("userId") Long userId,
            @Param("exerciseId") Long exerciseId);

    // Find user's personal records (max weight) for an exercise
    @Query("SELECT MAX(exerciseLog.weight) FROM ExerciseLog exerciseLog " +
            "JOIN exerciseLog.workoutSession workoutSession " +
            "WHERE workoutSession.user.id = :userId AND exerciseLog.exercise.id = :exerciseId " +
            "AND exerciseLog.setType = 'WORKING' AND exerciseLog.completed = true")
    Double findPersonalRecord(
            @Param("userId") Long userId,
            @Param("exerciseId") Long exerciseId);

    // Get exercise progress data for a time period
    @Query("SELECT workoutSession.startTime, MAX(exerciseLog.weight) FROM ExerciseLog exerciseLog " +
            "JOIN exerciseLog.workoutSession workoutSession " +
            "WHERE workoutSession.user.id = :userId AND exerciseLog.exercise.id = :exerciseId " +
            "AND workoutSession.startTime >= :startDate AND workoutSession.startTime <= :endDate " +
            "AND exerciseLog.setType = 'WORKING' AND exerciseLog.completed = true " +
            "GROUP BY workoutSession.startTime ORDER BY workoutSession.startTime")
    List<Object[]> getExerciseProgressData(
            @Param("userId") Long userId,
            @Param("exerciseId") Long exerciseId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    // Find most recent weight used for an exercise
    @Query("SELECT exerciseLog.weight FROM ExerciseLog exerciseLog " +
            "JOIN exerciseLog.workoutSession workoutSession " +
            "WHERE workoutSession.user.id = :userId AND exerciseLog.exercise.id = :exerciseId " +
            "AND exerciseLog.setType = 'WORKING' AND exerciseLog.completed = true " +
            "ORDER BY workoutSession.startTime DESC, exerciseLog.setNumber DESC")
    List<Double> findRecentWeights(
            @Param("userId") Long userId,
            @Param("exerciseId") Long exerciseId,
            Pageable pageable);

    // Get volume data (weight * reps * sets) for an exercise
    @Query("SELECT workoutSession.startTime, SUM(exerciseLog.weight * exerciseLog.reps) as volume " +
            "FROM ExerciseLog exerciseLog " +
            "JOIN exerciseLog.workoutSession workoutSession " +
            "WHERE workoutSession.user.id = :userId AND exerciseLog.exercise.id = :exerciseId " +
            "AND workoutSession.startTime >= :startDate AND workoutSession.startTime <= :endDate " +
            "AND exerciseLog.completed = true " +
            "GROUP BY workoutSession.startTime ORDER BY workoutSession.startTime")
    List<Object[]> getVolumeData(
            @Param("userId") Long userId,
            @Param("exerciseId") Long exerciseId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}