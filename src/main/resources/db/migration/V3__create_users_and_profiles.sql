CREATE TABLE users (
                            id BIGSERIAL PRIMARY KEY,
                            email VARCHAR(100) NOT NULL,
                            created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            CONSTRAINT uq_users_email UNIQUE (email)
);

CREATE TABLE profiles (
                          id               BIGSERIAL PRIMARY KEY,
                          user_id          BIGINT      NOT NULL,
                          goal             VARCHAR(20) NOT NULL,
                          experience_level VARCHAR(20) NOT NULL,
                          days_per_week    INTEGER     NOT NULL,
                          session_minutes  INTEGER     NOT NULL,
                          updated_at       TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_profiles_user      FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                          CONSTRAINT uq_profiles_user_id   UNIQUE (user_id),
                          CONSTRAINT chk_profiles_goal     CHECK (goal IN ('STRENGTH', 'MUSCLE', 'FAT_LOSS')),
                          CONSTRAINT chk_profiles_level    CHECK (experience_level IN ('BEGINNER')),
                          CONSTRAINT chk_profiles_days     CHECK (days_per_week BETWEEN 2 AND 5),
                          CONSTRAINT chk_profiles_minutes  CHECK (session_minutes IN (30, 45, 60, 75, 90))
);