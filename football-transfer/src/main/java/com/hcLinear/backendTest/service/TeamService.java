package com.hcLinear.backendTest.service;

import com.hcLinear.backendTest.dto.team.TeamListResponse;
import com.hcLinear.backendTest.exception.BusinessRuleViolationException;
import com.hcLinear.backendTest.exception.ConflictException;
import com.hcLinear.backendTest.exception.NotFoundException;
import com.hcLinear.backendTest.model.Player;
import com.hcLinear.backendTest.model.Team;
import com.hcLinear.backendTest.repository.PlayerRepository;
import com.hcLinear.backendTest.repository.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
	
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;


    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public List<TeamListResponse> findAll() {
        return teamRepository.findAllWithPlayersAndCaptain().stream()
                .map(t -> new TeamListResponse(
                        t.getId(),
                        t.getName(),
                        t.getPlayer() == null ? 0 : t.getPlayer().size(),
                        t.getCaptain() == null ? null : t.getCaptain().getFirstName() + " " + t.getCaptain().getLastName()
                ))
                .toList();
    }

    @Transactional
    public Team create(Team team) {
        if (teamRepository.existsByName(team.getName())) {
            throw new ConflictException("Team name already exists: " + team.getName());}
        return save(team);
    }

    public Team save(Team team) {
        return teamRepository.save(team);
    }

    public Team findById(long id){
        return getTeamOrThrow(id);
    }

    private Team getTeamOrThrow(long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Team not found: " + id));
    }


    @Transactional
    public void delete(long id) {
        getTeamOrThrow(id);
        teamRepository.deleteById(id);
    }

    @Transactional
    public Team setCaptain(long teamId, long captainId) {
        Team team = getTeamOrThrow(teamId);
        Player player = getPlayerOrThrow(captainId);
        if (player.getTeam() == null ||
                !player.getTeam().getId().equals(team.getId())) {
            throw new BusinessRuleViolationException("Captain must belong to the team");
        }
        team.setCaptain(player);
        return team;
    }

    private @NonNull Player getPlayerOrThrow(long captainId) {
        return playerRepository.findById(captainId)
                .orElseThrow(() -> new NotFoundException("Player not found: " + captainId));
    }


}
