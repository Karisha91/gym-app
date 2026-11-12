ALTER TABLE workout_exercises ADD COLUMN target_weight_kg DOUBLE PRECISION;

ALTER TABLE workout_exercises
    ADD CONSTRAINT ck_workout_exercises_target_weight CHECK (target_weight_kg >= 0);