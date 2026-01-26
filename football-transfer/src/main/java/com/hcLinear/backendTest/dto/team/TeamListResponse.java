package com.hcLinear.backendTest.dto.team;

public record TeamListResponse(
        Long id,
        String name,
        Integer playerCount,
        String captainFullName
) {}
