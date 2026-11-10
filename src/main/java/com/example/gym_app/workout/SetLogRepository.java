package com.example.gym_app.workout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SetLogRepository extends JpaRepository<SetLog, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from SetLog s where s.workoutExercise.id = :slotId")
    void deleteByWorkoutExerciseId(@Param("slotId") Long slotId);
}