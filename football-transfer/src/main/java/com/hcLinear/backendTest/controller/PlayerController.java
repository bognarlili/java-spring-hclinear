package com.hcLinear.backendTest.controller;

import com.hcLinear.backendTest.dto.player.PlayerCreateRequest;
import com.hcLinear.backendTest.dto.player.PlayerListResponse;
import com.hcLinear.backendTest.dto.player.PlayerResponse;
import com.hcLinear.backendTest.mapper.PlayerMapper;

import com.hcLinear.backendTest.model.Player;
import com.hcLinear.backendTest.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/players")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    public PlayerController(PlayerService playerService, PlayerMapper playerMapper) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
    }


    @GetMapping
    public List<PlayerListResponse> findAll() {
        return playerService.findAll();
    }



    @PostMapping
    public PlayerResponse create(@RequestBody @Valid PlayerCreateRequest playerCreateRequest) {

        Player player = playerMapper.dtoToPlayer(playerCreateRequest);
        Player savedPlayer = playerService.create(player, playerCreateRequest.teamId() );

        return playerMapper.toResponse(savedPlayer);
    }

    @GetMapping("/{id}")
    public PlayerResponse findById(@PathVariable long id){
        Player player = playerService.findById(id);

        return playerMapper.toResponse(player);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        playerService.delete(id);
    }



}
