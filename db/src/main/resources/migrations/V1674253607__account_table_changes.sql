ALTER TABLE account
    ALTER COLUMN hash DROP NOT NULL;

ALTER TABLE account
    ADD COLUMN source varchar(255);
