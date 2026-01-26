ALTER TABLE teams
    ADD COLUMN captain_id BIGINT NULL;

ALTER TABLE teams
    ADD CONSTRAINT fk_teams_captain
        FOREIGN KEY (captain_id)
            REFERENCES players (id)
            ON DELETE SET NULL;
