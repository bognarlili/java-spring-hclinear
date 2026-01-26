package com.hcLinear.backendTest.service;

import java.util.List;

import com.hcLinear.backendTest.dto.team.TeamListResponse;
import com.hcLinear.backendTest.model.Player;
import com.hcLinear.backendTest.model.Team;
import com.hcLinear.backendTest.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hcLinear.backendTest.dto.team.TeamResponse;
import com.hcLinear.backendTest.mapper.TeamMapper;
import com.hcLinear.backendTest.repository.TeamRepository;
import org.springframework.web.server.ResponseStatusException;

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
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Team name already exists: " + team.getName()
            );}
        return save(team);
    }

    public Team save(Team team) {
        return teamRepository.save(team);
    }

    public Team findById(long id){
        return teamRepository.findById(id).orElse(null);
    }


    @Transactional
    public void delete(long id) {
        teamRepository.deleteById(id);
    }

    @Transactional
    public Team setCaptain(Long teamId, Long captainId) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Team not found"));

        Player player = playerRepository.findById(captainId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Player not found"));

        if (player.getTeam() == null ||
                !player.getTeam().getId().equals(team.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Captain must belong to the team");
        }

        team.setCaptain(player);
        return team;
    }




}
