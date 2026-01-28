package com.hcLinear.backendTest.service;

import com.hcLinear.backendTest.dto.transfer.TransferRequestCreateRequest;
import com.hcLinear.backendTest.exception.BusinessRuleViolationException;
import com.hcLinear.backendTest.exception.NotFoundException;
import com.hcLinear.backendTest.model.*;
import com.hcLinear.backendTest.repository.PlayerRepository;
import com.hcLinear.backendTest.repository.TeamRepository;
import com.hcLinear.backendTest.repository.TransferDocumentRepository;
import com.hcLinear.backendTest.repository.TransferRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.hcLinear.backendTest.service.TransferRequestSpecification.*;

@Service
public class TransferRequestService {

    private final TransferRequestRepository transferRequestRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final TransferDocumentRepository transferDocumentRepository;
    private final TransferDocumentNumberGenerator documentNumberGenerator;


    public TransferRequestService(TransferRequestRepository transferRequestRepository, PlayerRepository playerRepository, TeamRepository teamRepository, TransferDocumentRepository transferDocumentRepository, TransferDocumentNumberGenerator documentNumberGenerator) {
        this.transferRequestRepository = transferRequestRepository;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.transferDocumentRepository = transferDocumentRepository;
        this.documentNumberGenerator = documentNumberGenerator;
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

    @Transactional
    public void reject(long requestId) {
        TransferRequest request = getRequestOrThrow(requestId);

        if (request.getStatus() != TransferRequestStatus.PENDING) {
            throw new BusinessRuleViolationException("Transfer request is already closed");
        }

        request.setStatus(TransferRequestStatus.REJECTED);
    }

    private TransferRequest getRequestOrThrow(long id) {
        return transferRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transfer request not found: " + id));
    }

    @Transactional
    public void accept(long requestId) {
        TransferRequest accepted = getRequestOrThrow(requestId);

        if (accepted.getStatus() != TransferRequestStatus.PENDING) {
            throw new BusinessRuleViolationException("Transfer request is already closed");
        }
        if (accepted.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BusinessRuleViolationException("Transfer request has expired");
        }

        Player player = accepted.getPlayer();
        Team toTeam = accepted.getToTeam();
        Team fromTeam = accepted.getFromTeam();
        BigDecimal fee = accepted.getFee();

        if (toTeam.getPlayer().size() >= 25) {
            throw new BusinessRuleViolationException("Target team already has 25 players");
        }

        if (toTeam.getBudget().compareTo(fee) < 0) {
            throw new BusinessRuleViolationException("Target team does not have enough budget");
        }

        toTeam.setBudget(toTeam.getBudget().subtract(fee));

        if (fromTeam != null) {
            fromTeam.setBudget(fromTeam.getBudget().add(fee));
        }

        player.setTeam(toTeam);

        accepted.setStatus(TransferRequestStatus.ACCEPTED);

        List<TransferRequest> others =
                transferRequestRepository.findByPlayerIdAndStatus(
                        accepted.getPlayer().getId(),
                        TransferRequestStatus.PENDING
                );

        for (TransferRequest other : others) {
            if (!other.getId().equals(accepted.getId())) {
                other.setStatus(TransferRequestStatus.CANCELLED);
            }
        }

        createTransferDocument(accepted);
    }

    @Transactional
    public List<TransferRequest> list(Long playerId, Long fromTeamId, Long toTeamId, TransferRequestStatus status) {
        Specification<TransferRequest> spec = Specification
                .where(TransferRequestSpecification.hasPlayerId(playerId))
                .and(TransferRequestSpecification.hasFromTeamId(fromTeamId))
                .and(TransferRequestSpecification.hasToTeamId(toTeamId))
                .and(TransferRequestSpecification.hasStatus(status));

        return transferRequestRepository.findAll(spec);
    }


    private void createTransferDocument(TransferRequest accepted) {

        TransferDocument doc = new TransferDocument();
        doc.setDocNumber(documentNumberGenerator.next());
        doc.setTransferDate(LocalDateTime.now());
        doc.setCreatedAt(LocalDateTime.now());
        doc.setPlayer(accepted.getPlayer());
        doc.setFromTeam(accepted.getFromTeam());
        doc.setToTeam(accepted.getToTeam());
        doc.setFee(accepted.getFee());
        doc.setTransferRequest(accepted);

        transferDocumentRepository.save(doc);
    }




}
