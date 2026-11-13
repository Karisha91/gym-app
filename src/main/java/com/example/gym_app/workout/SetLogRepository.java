package com.example.gym_app.workout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SetLogRepository extends JpaRepository<SetLog, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from SetLog s where s.workoutExercise.id = :slotId")
    void deleteByWorkoutExerciseId(@Param("slotId") Long slotId);

    @Query("""
            select s from SetLog s
            where s.workoutExercise.workout.userId = :userId
              and s.workoutExercise.exercise.id = :exerciseId
            order by s.workoutExercise.workout.createdAt desc,
                     s.workoutExercise.id desc,
                     s.setNumber asc
            """)
    List<SetLog> findHistory(@Param("userId") Long userId, @Param("exerciseId") Long exerciseId);
}