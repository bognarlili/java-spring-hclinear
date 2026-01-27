package com.hcLinear.backendTest.service;

import com.hcLinear.backendTest.dto.transfer.TransferRequestCreateRequest;
import com.hcLinear.backendTest.exception.BusinessRuleViolationException;
import com.hcLinear.backendTest.exception.NotFoundException;
import com.hcLinear.backendTest.model.Player;
import com.hcLinear.backendTest.model.Team;
import com.hcLinear.backendTest.model.TransferRequest;
import com.hcLinear.backendTest.model.TransferRequestStatus;
import com.hcLinear.backendTest.repository.PlayerRepository;
import com.hcLinear.backendTest.repository.TeamRepository;
import com.hcLinear.backendTest.repository.TransferRequestRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransferRequestService {

    private final TransferRequestRepository transferRequestRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public TransferRequestService(TransferRequestRepository transferRequestRepository, PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.transferRequestRepository = transferRequestRepository;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional
    public TransferRequest create(TransferRequestCreateRequest transferCreateRequest){

        Player player = getPlayerOrThrow(transferCreateRequest.playerId());
        Team toTeam = getTeamOrThrow(transferCreateRequest.toTeamId());
        Team fromTeam = transferCreateRequest.fromTeamId() != null ? getTeamOrThrow(transferCreateRequest.fromTeamId()) : null;

        if (fromTeam != null) {
            if (player.getTeam() == null || !player.getTeam().getId().equals(fromTeam.getId())) {
                throw new BusinessRuleViolationException("Source team must match player's current team");
            }
        }

        TransferRequest tr = new TransferRequest();
            tr.setCreatedAt(LocalDateTime.now());
            tr.setExpiresAt(transferCreateRequest.expiresAt());
            tr.setStatus(TransferRequestStatus.PENDING);
            tr.setPlayer(player);
            tr.setFromTeam(fromTeam);
            tr.setToTeam(toTeam);
            tr.setFee(transferCreateRequest.fee());

        return transferRequestRepository.save(tr);
    }

    private Player getPlayerOrThrow(long captainId) {
        return playerRepository.findById(captainId)
                .orElseThrow(() -> new NotFoundException("Player not found: " + captainId));
    }

    private Team getTeamOrThrow(long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Team not found: " + id));
    }



}
