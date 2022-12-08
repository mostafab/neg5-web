ALTER TABLE team_plays_in_tournament_match
    ADD COLUMN IF NOT EXISTS forfeit boolean;

-- Scores won't be recorded in the event of a forfeit
ALTER TABLE team_plays_in_tournament_match
    ALTER COLUMN score drop not null;
