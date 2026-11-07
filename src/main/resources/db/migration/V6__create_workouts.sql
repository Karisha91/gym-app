CREATE TABLE workouts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    goal VARCHAR(20) NOT NULL,
    session_minutes INT NOT NULL,

    CONSTRAINT fk_workouts_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT ck_workouts_goal CHECK (goal IN ('STRENGTH', 'MUSCLE', 'FAT_LOSS')),
    CONSTRAINT ck_workouts_session_minutes CHECK (session_minutes IN (30, 45, 60, 75, 90))
);

CREATE INDEX idx_workouts_user_created ON workouts (user_id, created_at DESC);