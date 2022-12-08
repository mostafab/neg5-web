DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'answer_type') THEN
        CREATE TYPE answer_type AS ENUM('Power', 'Base', 'Neg');
    END IF;
END$$;

CREATE CAST (varchar as answer_type) WITH INOUT AS IMPLICIT;
CREATE CAST (answer_type as varchar) WITH INOUT AS IMPLICIT;

ALTER TABLE tournament_tossup_values ALTER COLUMN tossup_answer_type TYPE answer_type USING tossup_answer_type::answer_type;


