CREATE TABLE profile_equipment (
                                   profile_id BIGINT      NOT NULL,
                                   equipment  VARCHAR(50) NOT NULL,
                                   CONSTRAINT pk_profile_equipment PRIMARY KEY (profile_id, equipment),
                                   CONSTRAINT fk_profile_equipment_profile FOREIGN KEY (profile_id) REFERENCES profiles (id) ON DELETE CASCADE,
                                   CONSTRAINT chk_profile_equipment_equipment CHECK (equipment IN ('BARBELL', 'DUMBBELL', 'MACHINE', 'CABLE', 'BODYWEIGHT'))
);