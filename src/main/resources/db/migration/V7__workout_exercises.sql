CREATE TABLE workout_exercises (
                                   id BIGSERIAL PRIMARY KEY,
                                   workout_id BIGINT NOT NULL,
                                   position INT NOT NULL,
                                   movement_pattern VARCHAR(20) NOT NULL,
                                   exercise_id BIGINT,
                                   sets INT NOT NULL,
                                   min_reps INT NOT NULL,
                                   max_reps INT NOT NULL,

                                   CONSTRAINT fk_workout_exercises_workout FOREIGN KEY (workout_id) REFERENCES workouts(id) ON DELETE CASCADE,
                                   CONSTRAINT fk_workout_exercises_exercise FOREIGN KEY (exercise_id) REFERENCES exercises(id),
                                   CONSTRAINT ck_workout_exercises_position CHECK (position BETWEEN 1 AND 8),
                                   CONSTRAINT ck_workout_exercises_pattern CHECK (movement_pattern IN ('SQUAT', 'HINGE', 'PUSH', 'PULL', 'CORE')),
                                   CONSTRAINT ck_workout_exercises_sets CHECK (sets > 0),
                                   CONSTRAINT ck_workout_exercises_reps CHECK (min_reps > 0 AND max_reps >= min_reps),
                                   CONSTRAINT uq_workout_exercises_position UNIQUE (workout_id, position)
);