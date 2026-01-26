package com.hcLinear.backendTest.dto.player;

public record PlayerListResponse(
        Long id,
        String fullName,
        String teamName
) {}
