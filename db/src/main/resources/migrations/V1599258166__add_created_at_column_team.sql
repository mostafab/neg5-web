ALTER TABLE tournament_team
    ADD COLUMN IF NOT EXISTS created_at timestamp DEFAULT NOW();

ALTER TABLE tournament_player
    ADD COLUMN IF NOT EXISTS created_at timestamp DEFAULT NOW();
