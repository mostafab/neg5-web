CREATE TABLE IF NOT EXISTS tournament_scoresheet (
  id bigserial PRIMARY KEY,
  status varchar(255) NOT NULL,
  tournament_id varchar(255) NOT NULL,
  team1_id varchar(255) NOT NULL,
  team2_id varchar(255) NOT NULL,
  round integer NOT NULL,
  room varchar(255),
  moderator varchar(255),
  packet varchar(255),
  notes text,
  tiebreaker boolean,
  phases text,
  created_at TIMESTAMP DEFAULT NOW(),
  added_by varchar(255) NOT NULL,
  last_updated_at TIMESTAMP DEFAULT NOW(),
  FOREIGN KEY (tournament_id) REFERENCES tournament(id) ON DELETE CASCADE,
  FOREIGN KEY (team1_id) REFERENCES tournament_team(id) ON DELETE CASCADE,
  FOREIGN KEY (team2_id) REFERENCES tournament_team(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS tournament_scoresheet_tournament_id_idx ON tournament_scoresheet(tournament_id);

CREATE TABLE IF NOT EXISTS tournament_scoresheet_cycle (
  id bigserial PRIMARY KEY,
  tournament_scoresheet_id bigint NOT NULL,
  number integer NOT NULL,
  stage varchar(255) NOT NULL,
  active_player_ids text NOT NULL,
  FOREIGN KEY (tournament_scoresheet_id) REFERENCES tournament_scoresheet(id) ON DELETE CASCADE,
  UNIQUE(tournament_scoresheet_id, number)
);

CREATE INDEX IF NOT EXISTS tournament_scoresheet_cycle_scoresheet_id_idx ON tournament_scoresheet_cycle(tournament_scoresheet_id);

CREATE TABLE IF NOT EXISTS tournament_scoresheet_cycle_answer (
  id bigserial PRIMARY KEY,
  tournament_scoresheet_cycle_id bigint NOT NULL,
  number integer NOT NULL,
  value integer NOT NULL,
  player_id varchar(255) NOT NULL,
  FOREIGN KEY (tournament_scoresheet_cycle_id) REFERENCES tournament_scoresheet_cycle(id) ON DELETE CASCADE,
  FOREIGN KEY (player_id) REFERENCES tournament_player(id) ON DELETE CASCADE,
  UNIQUE(tournament_scoresheet_cycle_id, number)
);

CREATE INDEX IF NOT EXISTS tournament_scoresheet_cycle_answer_cycle_id_idx ON tournament_scoresheet_cycle_answer(tournament_scoresheet_cycle_id);

CREATE TABLE IF NOT EXISTS tournament_scoresheet_cycle_bonus (
  id bigserial PRIMARY KEY,
  tournament_scoresheet_cycle_id bigint NOT NULL,
  number integer NOT NULL,
  value integer NOT NULL,
  answering_team_id varchar(255) NOT NULL,
  FOREIGN KEY (tournament_scoresheet_cycle_id) REFERENCES tournament_scoresheet_cycle(id) ON DELETE CASCADE,
  FOREIGN KEY (answering_team_id) REFERENCES tournament_team(id) ON DELETE CASCADE,
  UNIQUE(tournament_scoresheet_cycle_id, number)
);

CREATE INDEX IF NOT EXISTS tournament_scoresheet_cycle_bonus_cycle_id_idx ON tournament_scoresheet_cycle_bonus(tournament_scoresheet_cycle_id);




