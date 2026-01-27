package com.hcLinear.backendTest.dto.transfer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferRequestResponse(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime expiresAt,
        String status,
        Long playerId,
        Long fromTeamId,
        Long toTeamId,
        BigDecimal fee

) {
}
