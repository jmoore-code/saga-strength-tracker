package com.sagastrengthtracker.repository;

import com.sagastrengthtracker.model.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {

    // Find exercises for a specific workout day, ordered by exercise order
    List<WorkoutExercise> findByWorkoutDayIdOrderByExerciseOrderAsc(Long workoutDayId);

    // Find exercises in a workout program
    @Query("SELECT workoutExercise FROM WorkoutExercise workoutExercise " +
            "WHERE workoutExercise.workoutDay.workoutProgram.id = :programId " +
            "ORDER BY workoutExercise.workoutDay.dayNumber, workoutExercise.exerciseOrder")
    List<WorkoutExercise> findByWorkoutProgramId(@Param("programId") Long programId);

    // Find workout exercises by exercise id (find all workouts that use a specific exercise)
    List<WorkoutExercise> findByExerciseId(Long exerciseId);

    // Get the highest exercise order in a workout day (useful when adding new exercises)
    @Query("SELECT MAX(workoutExercise.exerciseOrder) FROM WorkoutExercise workoutExercise " +
            "WHERE workoutExercise.workoutDay.id = :workoutDayId")
    Integer findMaxExerciseOrderByWorkoutDayId(@Param("workoutDayId") Long workoutDayId);

    // Delete all exercises for a workout day
    void deleteByWorkoutDayId(Long workoutDayId);
}