CREATE SEQUENCE transfer_document_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE transfer_documents (
    id BIGINT PRIMARY KEY DEFAULT nextval('transfer_document_seq'),

    doc_number VARCHAR(20) NOT NULL,
    transfer_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,

    transfer_request_id BIGINT NOT NULL,
    player_id BIGINT NOT NULL,
    from_team_id BIGINT NULL,
    to_team_id BIGINT NOT NULL,

    fee NUMERIC(15,2) NOT NULL DEFAULT 0,

    CONSTRAINT uq_td_doc_number UNIQUE (doc_number),

    CONSTRAINT fk_td_transfer_request
        FOREIGN KEY (transfer_request_id) REFERENCES transfer_requests(id),

    CONSTRAINT fk_td_player
        FOREIGN KEY (player_id) REFERENCES players(id),

    CONSTRAINT fk_td_from_team
        FOREIGN KEY (from_team_id) REFERENCES teams(id),

    CONSTRAINT fk_td_to_team
        FOREIGN KEY (to_team_id) REFERENCES teams(id),

    CONSTRAINT chk_td_fee_non_negative CHECK (fee >= 0)
);

CREATE INDEX idx_td_transfer_request ON transfer_documents(transfer_request_id);
CREATE INDEX idx_td_player ON transfer_documents(player_id);
CREATE INDEX idx_td_to_team ON transfer_documents(to_team_id);
