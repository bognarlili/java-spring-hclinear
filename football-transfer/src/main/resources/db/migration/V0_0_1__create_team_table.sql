CREATE TABLE teams (
     id BIGSERIAL PRIMARY KEY,
     name VARCHAR(150) NOT NULL,
     city VARCHAR(100) NOT NULL,
     founded_year INTEGER NOT NULL,
     CONSTRAINT uq_team_name UNIQUE (name)
);
