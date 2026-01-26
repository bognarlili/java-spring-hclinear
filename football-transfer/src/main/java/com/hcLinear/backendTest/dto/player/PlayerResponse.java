package com.hcLinear.backendTest.dto.player;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.hcLinear.backendTest.model.PlayerPosition;

public record PlayerResponse(
        Long id,
		String firstName,
		String lastName,
        PlayerPosition playerPosition,
        Integer shirtNumber,
        LocalDate birthDate,
        BigDecimal marketValue,
        Long teamId
		
		) {

}
