package com.hcLinear.backendTest.dto.transfer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferRequestCreateRequest(
        @NotNull Long playerId,
        Long fromTeamId,
        @NotNull Long toTeamId,
        @NotNull LocalDateTime expiresAt,
        @PositiveOrZero BigDecimal fee
) {

}
