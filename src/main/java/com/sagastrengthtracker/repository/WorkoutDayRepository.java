package com.sagastrengthtracker.repository;

import com.sagastrengthtracker.model.WorkoutDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutDayRepository extends JpaRepository<WorkoutDay, Long> {

    // Find all workout days for a specific program, ordered by day number
    List<WorkoutDay> findByWorkoutProgramIdOrderByDayNumberAsc(Long programId);

    // Find next workout day after the specified day number
    @Query("SELECT workoutDay FROM WorkoutDay workoutDay WHERE workoutDay.workoutProgram.id = :programId " +
            "AND workoutDay.dayNumber > :currentDayNumber ORDER BY workoutDay.dayNumber ASC")
    List<WorkoutDay> findNextWorkoutDays(@Param("programId") Long programId,
                                         @Param("currentDayNumber") Integer currentDayNumber);

    // Count number of workout days in a program
    long countByWorkoutProgramId(Long programId);

    // Find workout days that are not rest days
    List<WorkoutDay> findByWorkoutProgramIdAndIsRestDayFalseOrderByDayNumberAsc(Long programId);

    // Find workout days by name (for searching)
    List<WorkoutDay> findByWorkoutProgramIdAndNameContainingIgnoreCase(Long programId, String name);

    // Delete all workout days for a program (used when deleting a program)
    void deleteByWorkoutProgramId(Long programId);
}