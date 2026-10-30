CREATE TABLE exercises (
                           id               BIGSERIAL PRIMARY KEY,
                           name             VARCHAR(255) NOT NULL UNIQUE,
                           muscle_group     VARCHAR(50) NOT NULL
                               CHECK (muscle_group IN ('CHEST', 'BACK', 'SHOULDERS', 'BICEPS', 'TRICEPS',
                                                       'QUADS', 'HAMSTRINGS', 'GLUTES', 'CALVES', 'CORE')),
                           movement_pattern VARCHAR(50) NOT NULL
                               CHECK (movement_pattern IN ('SQUAT', 'HINGE', 'PUSH', 'PULL', 'CORE')),
                           equipment        VARCHAR(50) NOT NULL
                               CHECK (equipment IN ('BARBELL', 'DUMBBELL', 'MACHINE', 'CABLE', 'BODYWEIGHT')),
                           is_compound      BOOLEAN NOT NULL DEFAULT FALSE,
                           instructions     TEXT NOT NULL
);