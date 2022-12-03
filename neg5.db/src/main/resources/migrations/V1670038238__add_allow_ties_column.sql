ALTER TABLE tournament
    ADD COLUMN IF NOT EXISTS allow_ties boolean;
