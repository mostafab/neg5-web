CREATE TABLE IF NOT EXISTS tournament_team_group (
  id bigserial PRIMARY KEY,
  tournament_id varchar(255) NOT NULL REFERENCES tournament(id),
  name varchar(255) NOT NULL,
  state_code varchar(255)
);

ALTER TABLE tournament_team
    ADD COLUMN team_group_id bigint;

CREATE INDEX IF NOT EXISTS tournament_team_group_tournament_id_idx ON tournament_team_group(tournament_id);




