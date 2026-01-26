package com.hcLinear.backendTest.service;

import com.hcLinear.backendTest.dto.player.PlayerListResponse;
import com.hcLinear.backendTest.dto.player.PlayerResponse;
import com.hcLinear.backendTest.dto.team.TeamResponse;
import com.hcLinear.backendTest.mapper.PlayerMapper;
import com.hcLinear.backendTest.mapper.TeamMapper;
import com.hcLinear.backendTest.model.Player;
import com.hcLinear.backendTest.model.Team;
import com.hcLinear.backendTest.repository.PlayerRepository;
import com.hcLinear.backendTest.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private final TeamRepository teamRepository;

    public PlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
        this.teamRepository = teamRepository;
    }

    public List<PlayerListResponse> findAll() {
        return playerMapper.toListResponses(playerRepository.findAll());
    }


    @Transactional
    public Player create(Player player, Long teamId) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Team not found: " + teamId));

        player.setTeam(team);

        return save(player);
    }

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public Player findById(long id){
        return playerRepository.findById(id).orElse(null);
    }


    @Transactional
    public void delete(long id) {
        playerRepository.deleteById(id);
    }


}
