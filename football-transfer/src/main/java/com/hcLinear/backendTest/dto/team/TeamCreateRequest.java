package com.hcLinear.backendTest.dto.team;

import java.math.BigDecimal;
import java.time.Year;

import com.hcLinear.backendTest.validation.MinYear;
import jakarta.validation.constraints.*;

public record TeamCreateRequest(
		@NotBlank @Size(max = 150) String name,
		@NotBlank @Size(max = 100) String city,
		@NotNull @PastOrPresent @MinYear(1800) Year foundedYear,
		@NotNull @DecimalMin("0.00") BigDecimal budget
		
		) {}
