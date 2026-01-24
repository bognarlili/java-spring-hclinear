package com.hcLinear.backendTest.dto.player;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.hcLinear.backendTest.model.PlayerPosition;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record PlayerCreateRequest(
	    @NotBlank String firstName,
	    @NotBlank String lastName,
	    @NotNull PlayerPosition playerPosition,
	    @NotNull @Min(1) @Max(99) Integer shirtNumber,
	    @NotNull @Past LocalDate birthDate,
	    @NotNull @DecimalMin("0.00") BigDecimal marketValue,
	    Long teamId
) {
	
}
