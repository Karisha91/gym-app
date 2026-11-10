CREATE TABLE set_logs (
                          id BIGSERIAL PRIMARY KEY,
                          workout_exercise_id BIGINT NOT NULL,
                          set_number INT NOT NULL,
                          weight_kg DOUBLE PRECISION NOT NULL,
                          reps INT NOT NULL,
                          logged_at TIMESTAMPTZ NOT NULL DEFAULT now(),

                          CONSTRAINT fk_set_logs_workout_exercise FOREIGN KEY (workout_exercise_id) REFERENCES workout_exercises(id) ON DELETE CASCADE,
                          CONSTRAINT uq_set_logs_set UNIQUE (workout_exercise_id, set_number),
                          CONSTRAINT ck_set_logs_set_number CHECK (set_number BETWEEN 1 AND 10),
                          CONSTRAINT ck_set_logs_weight CHECK (weight_kg >= 0),
                          CONSTRAINT ck_set_logs_reps CHECK (reps > 0)
);