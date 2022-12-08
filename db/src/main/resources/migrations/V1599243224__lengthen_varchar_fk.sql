ALTER TABLE match_is_part_of_phase
    ALTER COLUMN tournament_id type VARCHAR(255);

ALTER TABLE player_match_tossup
    ALTER COLUMN tournament_id type VARCHAR(255);

ALTER TABLE player_plays_in_tournament_match
    ALTER COLUMN tournament_id type VARCHAR(255);

ALTER TABLE team_plays_in_tournament_match
    ALTER COLUMN tournament_id type VARCHAR(255);

ALTER TABLE tournament_division
    ALTER COLUMN tournament_id type VARCHAR(255);

ALTER TABLE tournament_match
    ALTER COLUMN tournament_id type VARCHAR(255);

ALTER TABLE tournament_phase
    ALTER COLUMN tournament_id type VARCHAR(255);

ALTER TABLE tournament_player
    ALTER COLUMN tournament_id type VARCHAR(255);

ALTER TABLE tournament_stat_report
    ALTER COLUMN tournament_id type VARCHAR(255);

ALTER TABLE tournament_team
    ALTER COLUMN tournament_id type VARCHAR(255);

ALTER TABLE tournament_team_in_division
    ALTER COLUMN tournament_id type VARCHAR(255);

ALTER TABLE tournament_tossup_values
    ALTER COLUMN tournament_id type VARCHAR(255);

ALTER TABLE user_collaborates_on_tournament
    ALTER COLUMN tournament_id type VARCHAR(255);
