ALTER TABLE tournament_phase
    ADD COLUMN created_at TIMESTAMP DEFAULT NOW(),
    ADD COLUMN added_by varchar(255);

ALTER TABLE tournament_division
    ADD COLUMN created_at TIMESTAMP DEFAULT NOW(),
    ADD COLUMN added_by varchar(255);

ALTER TABLE tournament
    ADD COLUMN created_at TIMESTAMP DEFAULT NOW(),
    ADD COLUMN added_by varchar(255);