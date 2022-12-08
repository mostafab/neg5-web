CREATE TABLE IF NOT EXISTS account (
    username varchar(20) NOT NULL,
    hash varchar(255) NOT NULL,
    name varchar(50),
    email varchar(50),
    hidden boolean DEFAULT false,
    UNIQUE(email),
    PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS tournament (
    id varchar(20) NOT NULL,
    name varchar(255) NOT NULL CHECK (char_length(name) > 0),
    tournament_date date,
    location varchar(50),
    question_set varchar(255),
    comments text,
    hidden boolean,
    director_id varchar(20) NOT NULL,
    bouncebacks boolean DEFAULT false,
    max_active_players_per_team integer DEFAULT 4,
    bonus_point_value integer NOT NULL DEFAULT 10 CHECK (bonus_point_value > 0),
    parts_per_bonus integer NOT NULL DEFAULT 3 CHECK (parts_per_bonus > 0),
    active_phase_id varchar(20),
    PRIMARY KEY (id),
    FOREIGN KEY (director_id) REFERENCES account(username) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS tournament_team (
    id varchar(20) NOT NULL,
    name varchar(50) NOT NULL CHECK (char_length(name) > 0),
    tournament_id varchar(20) NOT NULL,
    added_by varchar(20) NOT NULL,
    PRIMARY KEY (id, tournament_id),
    FOREIGN KEY (tournament_id) REFERENCES tournament(id) ON DELETE CASCADE,
    FOREIGN KEY (added_by) REFERENCES account(username) ON DELETE SET NULL,
    UNIQUE (name, tournament_id)
);

CREATE TABLE IF NOT EXISTS tournament_match (
    id varchar(20) NOT NULL,
    tournament_id varchar(20) NOT NULL,
    round integer DEFAULT 0 CHECK (round >= 0),
    room VARCHAR(20),
    moderator VARCHAR(20),
    packet VARCHAR(20),
    tossups_heard integer DEFAULT 0 CHECK (tossups_heard >= 0),
    notes varchar(1000),
    scoresheet json[],
    added_by varchar(20) NOT NULL,
    PRIMARY KEY (id, tournament_id),
    FOREIGN KEY (tournament_id) REFERENCES tournament(id) ON DELETE CASCADE,
    FOREIGN KEY (added_by) REFERENCES account(username) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS tournament_player (
    id varchar(20) NOT NULL,
    name varchar(50) NOT NULL CHECK (char_length(name) > 0),
    team_id varchar(20) NOT NULL,
    tournament_id varchar(20) NOT NULL,
    added_by varchar(20) NOT NULL,
    PRIMARY KEY (id, tournament_id),
    FOREIGN KEY (team_id, tournament_id) REFERENCES tournament_team(id, tournament_id) ON DELETE CASCADE,
    FOREIGN KEY (added_by) REFERENCES account(username) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS user_collaborates_on_tournament (
    username varchar(20) NOT NULL,
    tournament_id varchar(20) NOT NULL,
    is_admin boolean DEFAULT false,
    PRIMARY KEY (username, tournament_id),
    FOREIGN KEY (username) REFERENCES account(username) ON DELETE CASCADE,
    FOREIGN KEY (tournament_id) REFERENCES tournament(id) ON DELETE CASCADE
);

-- CREATE TYPE IF NOT EXISTS answer_type AS ENUM('Power', 'Base', 'Neg');

CREATE TABLE IF NOT EXISTS tournament_tossup_values (
    tournament_id varchar(20) NOT NULL,
    tossup_value integer NOT NULL,
    tossup_answer_type VARCHAR(255) NOT NULL,
    PRIMARY KEY (tournament_id, tossup_value),
    FOREIGN KEY (tournament_id) REFERENCES tournament(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS player_plays_in_tournament_match (
    player_id varchar(20) NOT NULL,
    match_id varchar(20) NOT NULL,
    tournament_id varchar(20) NOT NULL,
    tossups_heard integer DEFAULT 0 CHECK (tossups_heard >= 0),
    PRIMARY KEY (player_id, match_id, tournament_id),
    FOREIGN KEY (player_id, tournament_id) REFERENCES tournament_player(id, tournament_id),
    FOREIGN KEY (match_id, tournament_id) REFERENCES tournament_match(id, tournament_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS team_plays_in_tournament_match (
    team_id varchar(20) NOT NULL,
    match_id varchar(20) NOT NULL,
    tournament_id varchar(20) NOT NULL,
    score integer NOT NULL,
    bounceback_points integer DEFAULT 0 CHECK (bounceback_points >= 0),
    overtime_tossups_gotten integer DEFAULT 0 CHECK (overtime_tossups_gotten >= 0),
    PRIMARY KEY (team_id, match_id, tournament_id),
    FOREIGN KEY (team_id, tournament_id) REFERENCES tournament_team(id, tournament_id),
    FOREIGN KEY (match_id, tournament_id) REFERENCES tournament_match(id, tournament_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS player_match_tossup (
    player_id varchar(20) NOT NULL,
    match_id varchar(20) NOT NULL,
    tournament_id varchar(20) NOT NULL,
    tossup_value integer NOT NULL,
    number_gotten integer DEFAULT 0 NOT NULL CHECK (number_gotten >= 0),
    PRIMARY KEY (player_id, match_id, tournament_id, tossup_value),
    FOREIGN KEY (player_id, tournament_id) REFERENCES tournament_player(id, tournament_id),
    FOREIGN KEY (match_id, tournament_id) REFERENCES tournament_match(id, tournament_id) ON DELETE CASCADE,
    FOREIGN KEY (tournament_id, tossup_value) REFERENCES tournament_tossup_values(tournament_id, tossup_value),
    FOREIGN KEY (player_id, match_id, tournament_id) REFERENCES player_plays_in_tournament_match(player_id, match_id, tournament_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tournament_phase (
    id varchar(20) NOT NULL,
    name varchar(50) NOT NULL CHECK (char_length(name) > 0),
    tournament_id varchar(20) NOT NULL,
    PRIMARY KEY (id, tournament_id),
    FOREIGN KEY (tournament_id) REFERENCES tournament(id) ON DELETE CASCADE,
    UNIQUE (name, tournament_id)
);

CREATE TABLE IF NOT EXISTS match_is_part_of_phase (
    match_id varchar(20) NOT NULL,
    tournament_id varchar(20) NOT NULL,
    phase_id varchar(20) NOT NULL,
    PRIMARY KEY (match_id, tournament_id, phase_id),
    FOREIGN KEY (match_id, tournament_id) REFERENCES tournament_match(id, tournament_id) ON DELETE CASCADE,
    FOREIGN KEY (phase_id, tournament_id) REFERENCES tournament_phase(id, tournament_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tournament_division (
    id varchar(20) NOT NULL,
    name varchar(50) NOT NULL CHECK (char_length(name) > 0),
    tournament_id varchar(20) NOT NULL,
    phase_id varchar(20) NOT NULL,
    PRIMARY KEY (id, tournament_id),
    FOREIGN KEY (phase_id, tournament_id) REFERENCES tournament_phase(id, tournament_id) ON DELETE CASCADE,
    UNIQUE (tournament_id, phase_id, name)
);

CREATE TABLE IF NOT EXISTS tournament_team_in_division (
    team_id varchar(20) NOT NULL,
    division_id varchar(20) NOT NULL,
    tournament_id varchar(20) NOT NULL,
    PRIMARY KEY (team_id, division_id, tournament_id),
    FOREIGN KEY (division_id, tournament_id) REFERENCES tournament_division(id, tournament_id) ON DELETE CASCADE,
    FOREIGN KEY (team_id, tournament_id) REFERENCES tournament_team(id, tournament_id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS hash_on_director_id ON tournament (director_id);

CREATE INDEX IF NOT EXISTS hash_on_tournament_team_tournament_id ON tournament_team (tournament_id);

CREATE INDEX IF NOT EXISTS hash_on_tournament_match_tournament_id ON tournament_match (tournament_id);
CREATE INDEX IF NOT EXISTS index_on_tournament_match_round ON tournament_match (round);

CREATE INDEX IF NOT EXISTS hash_on_tournament_player_tournament_id ON tournament_player (tournament_id);

CREATE INDEX IF NOT EXISTS hash_on_user_collaborates_username ON user_collaborates_on_tournament (username);

CREATE INDEX IF NOT EXISTS hash_on_tournament_tossup_values_tournament_id ON tournament_tossup_values (tournament_id);

CREATE INDEX IF NOT EXISTS hash_on_player_plays_in_match_tournament_id ON player_plays_in_tournament_match (tournament_id);
CREATE INDEX IF NOT EXISTS hash_on_player_plays_in_match_player_id ON player_plays_in_tournament_match (player_id);

CREATE INDEX IF NOT EXISTS hash_on_team_plays_in_match_tournament_id ON team_plays_in_tournament_match (tournament_id);
CREATE INDEX IF NOT EXISTS hash_on_team_plays_in_match_team_id ON team_plays_in_tournament_match (team_id);

CREATE INDEX IF NOT EXISTS hash_on_player_match_tossup_tournament_id ON player_match_tossup (tournament_id);
CREATE INDEX IF NOT EXISTS hash_on_player_match_tossup_player_id ON player_match_tossup (player_id);
CREATE INDEX IF NOT EXISTS hash_on_player_match_tossup_match_id ON player_match_tossup (match_id);

CREATE INDEX IF NOT EXISTS hash_on_tournament_phase_tournament_id ON tournament_phase (tournament_id);

-- id
CREATE INDEX IF NOT EXISTS tournament_id_idx ON tournament(id);

-- create index on tournament_id for all tables referencing tournament_id
CREATE INDEX IF NOT EXISTS user_collaborates_on_tournament_tournament_id_idx ON user_collaborates_on_tournament(tournament_id);
CREATE INDEX IF NOT EXISTS tournament_tossup_values_tournament_id_idx ON tournament_tossup_values(tournament_id);
CREATE INDEX IF NOT EXISTS tournament_team_in_division_tournament_id_idx ON tournament_team_in_division(tournament_id);
CREATE INDEX IF NOT EXISTS tournament_team_tournament_id_idx ON tournament_team(tournament_id);
CREATE INDEX IF NOT EXISTS tournament_player_tournament_id_idx ON tournament_player(tournament_id);
CREATE INDEX IF NOT EXISTS tournament_phase_tournament_id_idx ON tournament_phase(tournament_id);
CREATE INDEX IF NOT EXISTS tournament_match_tournament_id_idx ON tournament_match(tournament_id);
CREATE INDEX IF NOT EXISTS tournament_division_tournament_id_idx ON tournament_division(tournament_id);
CREATE INDEX IF NOT EXISTS team_plays_in_tournament_match_tournament_id_idx ON team_plays_in_tournament_match(tournament_id);
CREATE INDEX IF NOT EXISTS player_plays_in_tournament_match_tournament_id_idx ON player_plays_in_tournament_match(tournament_id);
CREATE INDEX IF NOT EXISTS player_match_tossup_tournament_id_idx ON player_match_tossup(tournament_id);
CREATE INDEX IF NOT EXISTS match_is_part_of_phase_tournament_id_idx ON match_is_part_of_phase(tournament_id);

-- CREATE TYPE stats_report_type AS ENUM('team', 'individual', 'team_full', 'individual_full', 'round_report');

CREATE TABLE IF NOT EXISTS tournament_stat_report (
  id bigserial,
  tournament_id varchar(20) NOT NULL,
  phase_id varchar(20),
  statistics json NOT NULL,
  report_type VARCHAR(255),
  created_at timestamp DEFAULT NOW() NOT NULL,
  last_updated_at timestamp DEFAULT NOW() NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (tournament_id) REFERENCES tournament(id) ON DELETE CASCADE,
  FOREIGN KEY (tournament_id, phase_id) REFERENCES tournament_phase(tournament_id, id) ON DELETE CASCADE,
  UNIQUE(tournament_id, phase_id, report_type)
);

CREATE INDEX IF NOT EXISTS tournament_stat_report_tournament_id ON tournament_stat_report(tournament_id);

ALTER TABLE tournament_match
  ADD COLUMN IF NOT EXISTS serial_id varchar default NULL,
  ADD COLUMN IF NOT EXISTS added_at timestamp DEFAULT NOW(),
  ADD COLUMN IF NOT EXISTS last_updated_at timestamp DEFAULT NOW();

ALTER TABLE tournament_match
    DROP CONSTRAINT IF EXISTS unique_serial_id_tournament_id;

ALTER TABLE tournament_match
    ADD CONSTRAINT unique_serial_id_tournament_id UNIQUE(serial_id, tournament_id);

ALTER table player_match_tossup
    DROP CONSTRAINT IF EXISTS player_match_tossup_player_id_fkey;

ALTER TABLE player_match_tossup
    ADD CONSTRAINT player_match_tossup_player_id_fkey
    FOREIGN KEY (player_id, tournament_id)
    REFERENCES tournament_player(id, tournament_id)
    ON DELETE CASCADE;

ALTER TABLE player_match_tossup
    DROP CONSTRAINT IF EXISTS player_match_tossup_player_plays_in_tournament_match_fkey;

ALTER TABLE player_match_tossup
    ADD CONSTRAINT player_match_tossup_player_plays_in_tournament_match_fkey
    FOREIGN KEY (player_id, match_id, tournament_id)
    REFERENCES player_plays_in_tournament_match(player_id, match_id, tournament_id)
    ON DELETE CASCADE;

ALTER TABLE player_plays_in_tournament_match
    DROP CONSTRAINT IF EXISTS player_plays_in_tournament_match_player_id_fkey;

ALTER TABLE player_plays_in_tournament_match
    DROP CONSTRAINT IF EXISTS player_plays_in_tournament_match_tournament_player_fkey;

ALTER TABLE player_plays_in_tournament_match
    ADD CONSTRAINT player_plays_in_tournament_match_tournament_player_fkey
    FOREIGN KEY (player_id, tournament_id)
    REFERENCES tournament_player(id, tournament_id)
    ON DELETE CASCADE;

ALTER TABLE tournament_team
	ALTER COLUMN id type varchar(255);

ALTER TABLE tournament_team_in_division
	ALTER COLUMN team_id type varchar(255);

ALTER TABLE team_plays_in_tournament_match
	ALTER COLUMN team_id type varchar(255);

ALTER TABLE tournament_player
    ALTER COLUMN team_id type varchar(255);

ALTER TABLE tournament_player
	ALTER COLUMN id type varchar(255);

ALTER TABLE player_plays_in_tournament_match
    ALTER COLUMN player_id type varchar(255);

ALTER TABLE player_match_tossup
    ALTER COLUMN player_id type varchar(255);

ALTER TABLE tournament_match
	ALTER COLUMN id type varchar(255);

ALTER TABLE team_plays_in_tournament_match
    ALTER COLUMN match_id type varchar(255);

ALTER TABLE player_plays_in_tournament_match
    ALTER COLUMN match_id type varchar(255);

ALTER TABLE player_match_tossup
    ALTER COLUMN match_id type varchar(255);

ALTER TABLE match_is_part_of_phase
    ALTER COLUMN match_id type varchar(255);

ALTER TABLE tournament_phase
    ALTER COLUMN id type varchar(255);

ALTER TABLE tournament_division
    ALTER COLUMN phase_id type varchar(255);

ALTER TABLE match_is_part_of_phase
    ALTER COLUMN phase_id type varchar(255);

ALTER TABLE tournament
    ALTER COLUMN active_phase_id type varchar(255);

ALTER TABLE tournament_stat_report
    ALTER COLUMN phase_id type varchar(255);

delete from tournament_match where id in (
select id
from tournament_match
group by id
having count(*) > 1 );

CREATE UNIQUE INDEX IF NOT EXISTS tournament_match_id_unique on tournament_match(id);

delete from team_plays_in_tournament_match where team_id in (
select id
from tournament_team
group by id
having count(*) > 1
);

delete from tournament_team where id in (
select id
from tournament_team
group by id
having count(*) > 1);

CREATE UNIQUE INDEX IF NOT EXISTS tournament_team_id_unique on tournament_team(id);

delete from tournament_player where id in (
select id
from tournament_player
group by id
having count(*) > 1);

CREATE UNIQUE INDEX IF NOT EXISTS tournament_player_id_unique on tournament_player(id);

delete from tournament_division where id in (
select id
from tournament_division
group by id
having count(*) > 1);

CREATE UNIQUE INDEX IF NOT EXISTS tournament_division_id_unique on tournament_division(id);

delete from tournament_phase where id in (
select id
from tournament_phase
group by id
having count(*) > 1);

CREATE UNIQUE INDEX IF NOT EXISTS tournament_phase_id_unique on tournament_phase(id);

alter table account alter column username type varchar(255);
alter table account add column if not exists mongo_id varchar(200);

alter table tournament alter column director_id type varchar(255);
alter table tournament_team alter column added_by type varchar(255);
alter table tournament_match alter column added_by type varchar(255);
alter table tournament_player alter column added_by type varchar(255);
alter table user_collaborates_on_tournament alter column username type varchar(255);

alter table tournament_phase alter column name type varchar(255);
alter table tournament_division alter column name type varchar(255);

alter table tournament_team alter column name type varchar(255);
alter table tournament_player alter column name type varchar(255);

alter table tournament_match alter column room type varchar(255);
alter table tournament_match alter column moderator type varchar(255);
alter table tournament_match alter column packet type varchar(255);

alter table tournament add column if not exists mongo_id varchar(200);

alter table tournament_division alter column id type varchar(255);
alter table tournament_team_in_division alter column division_id type varchar(255);

