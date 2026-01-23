CREATE TABLE players (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    position   VARCHAR(2)   NOT NULL,
    shirt_number INTEGER    NOT NULL,
    birth_date DATE         NOT NULL,
    market_value NUMERIC(15,2) NOT NULL DEFAULT 0,

    team_id BIGINT NULL,
    CONSTRAINT fk_players_team
        FOREIGN KEY (team_id)
        REFERENCES teams (id)
        ON DELETE SET NULL,

    CONSTRAINT chk_player_position
        CHECK (position IN ('GK','DF','MF','FW')),

    CONSTRAINT chk_player_shirt_number
        CHECK (shirt_number BETWEEN 1 AND 99),

    CONSTRAINT chk_player_market_value
        CHECK (market_value >= 0)
);

CREATE INDEX idx_players_team_id ON players(team_id);