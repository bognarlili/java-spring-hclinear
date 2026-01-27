package com.hcLinear.backendTest.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransferDocumentNumberGenerator {

    private static final String PREFIX = "ATIG";
    private static final String SUFFIX = "HU";
    private static final int SERIAL_LENGTH = 6;

    private final JdbcTemplate jdbcTemplate;

    public TransferDocumentNumberGenerator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String next() {
        Long nextVal = jdbcTemplate.queryForObject(
                "SELECT nextval('transfer_document_seq')", Long.class);

        if (nextVal == null) {
            throw new IllegalStateException("Could not fetch nextval from transfer_document_seq");
        }

        String serial = String.format("%0" + SERIAL_LENGTH + "d", nextVal);
        return PREFIX + serial + SUFFIX;
    }
}
