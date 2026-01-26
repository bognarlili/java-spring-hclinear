package com.hcLinear.backendTest.dto.team;

import java.math.BigDecimal;
import java.time.Year;

public record TeamResponse(
        Long id,
        String name,
        String city,
        Year foundedYear,
        BigDecimal budget,
        Long captainId) {

}
