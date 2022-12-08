CREATE INDEX IF NOT EXISTS tournament_player_team_id_idx ON tournament_player(team_id);
CREATE INDEX IF NOT EXISTS tournament_team_in_division_team_id_idx on tournament_team_in_division(team_id);
