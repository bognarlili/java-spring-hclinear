CREATE TABLE transfer_requests (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    player_id BIGINT NOT NULL,
    from_team_id BIGINT NULL,
    to_team_id BIGINT NOT NULL,
    fee NUMERIC(15,2) NOT NULL DEFAULT 0,

    CONSTRAINT fk_tr_player FOREIGN KEY (player_id) REFERENCES players(id),
    CONSTRAINT fk_tr_from_team FOREIGN KEY (from_team_id) REFERENCES teams(id),
    CONSTRAINT fk_tr_to_team FOREIGN KEY (to_team_id) REFERENCES teams(id),

    CONSTRAINT chk_tr_status CHECK (status IN ('PENDING','ACCEPTED','REJECTED','CANCELLED')),
    CONSTRAINT chk_tr_fee_non_negative CHECK (fee >= 0),
    CONSTRAINT chk_tr_expiry_after_created CHECK (expires_at > created_at)
);

CREATE INDEX idx_tr_player ON transfer_requests(player_id);
CREATE INDEX idx_tr_from_team ON transfer_requests(from_team_id);
CREATE INDEX idx_tr_to_team ON transfer_requests(to_team_id);
CREATE INDEX idx_tr_status ON transfer_requests(status);

