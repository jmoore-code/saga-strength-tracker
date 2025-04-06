package com.sagastrengthtracker.repository;

import com.sagastrengthtracker.model.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    // Find by name
    Optional<Tag> findByNameIgnoreCase(String name);

    // Find or create a tag
    default Tag findOrCreate(String name) {
        return findByNameIgnoreCase(name).orElseGet(() -> {
            Tag newTag = new Tag();
            newTag.setName(name);
            return save(newTag);
        });
    }

    // Find all tags for a workout program
    @Query("SELECT tag FROM Tag tag JOIN tag.workoutPrograms workoutProgram " +
            "WHERE workoutProgram.id = :programId")
    List<Tag> findByWorkoutProgramId(@Param("programId") Long programId);

    // Find popular tags (useful for tag clouds or suggestions)
    @Query("SELECT tag, COUNT(workoutProgram) as programCount FROM Tag tag " +
            "JOIN tag.workoutPrograms workoutProgram " +
            "GROUP BY tag ORDER BY programCount DESC")
    List<Object[]> findPopularTags(Pageable pageable);

    // Find tags matching a name pattern
    List<Tag> findByNameContainingIgnoreCase(String pattern);
}