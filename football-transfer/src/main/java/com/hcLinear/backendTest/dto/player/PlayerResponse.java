package com.hcLinear.backendTest.dto.player;

import com.hcLinear.backendTest.model.PlayerPosition;

import java.math.BigDecimal;
import java.time.LocalDate;

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
