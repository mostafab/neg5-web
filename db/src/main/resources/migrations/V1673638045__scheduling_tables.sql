ALTER TABLE tournament_phase ADD CONSTRAINT tournament_phase_unique_id UNIQUE(id);

CREATE TABLE IF NOT EXISTS tournament_schedule (
  id bigserial PRIMARY KEY,
  tournament_id varchar(255) NOT NULL REFERENCES tournament(id) ON DELETE CASCADE,
  phase_id varchar(255) NOT NULL REFERENCES tournament_phase(id) ON DELETE CASCADE,
  UNIQUE(tournament_id, phase_id)
);

CREATE INDEX IF NOT EXISTS tournament_schedule_tournament_id_idx ON tournament_schedule(tournament_id);

CREATE TABLE IF NOT EXISTS tournament_schedule_match (
    id bigserial PRIMARY KEY,
    tournament_schedule_id bigint NOT NULL REFERENCES tournament_schedule(id),
    team1_id varchar(255) REFERENCES tournament_team(id) ON DELETE CASCADE,
    team2_id varchar(255) REFERENCES tournament_team(id) ON DELETE CASCADE,
    round integer NOT NULL,
    room varchar(255)
);

CREATE INDEX IF NOT EXISTS tournament_schedule_match_tournament_schedule_id_idx ON tournament_schedule_match(tournament_schedule_id);



