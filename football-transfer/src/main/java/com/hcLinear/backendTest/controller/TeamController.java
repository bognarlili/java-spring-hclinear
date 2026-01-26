package com.hcLinear.backendTest.controller;

import java.util.List;

import com.hcLinear.backendTest.dto.team.TeamCaptainRequest;
import com.hcLinear.backendTest.dto.team.TeamCreateRequest;
import com.hcLinear.backendTest.dto.team.TeamListResponse;
import com.hcLinear.backendTest.mapper.TeamMapper;
import com.hcLinear.backendTest.model.Team;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hcLinear.backendTest.dto.team.TeamResponse;
import com.hcLinear.backendTest.service.TeamService;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
	
	 private final TeamService teamService;
	 private final TeamMapper teamMapper;

	public TeamController(TeamService teamService, TeamMapper teamMapper) {
		this.teamService = teamService;
		this.teamMapper = teamMapper;
	}

	@GetMapping
	    public List<TeamListResponse> findAll() {
	        return teamService.findAll();
	    }



	@PostMapping
	public TeamResponse create(@RequestBody @Valid TeamCreateRequest teamCreateRequest) {

		Team team = teamMapper.dtoToTeam(teamCreateRequest);
		Team savedTeam = teamService.create(team);

		return teamMapper.toResponse(savedTeam);
	}

	@GetMapping("/{id}")
	public TeamResponse findById(@PathVariable long id){
		Team team = teamService.findById(id);

		return teamMapper.toResponse(team);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		teamService.delete(id);
	}

	@PatchMapping("/{id}/captain")
	public TeamResponse setCaptain(@PathVariable Long id,
								   @RequestBody TeamCaptainRequest request) {
		Team updated = teamService.setCaptain(id, request.captainId());
		return teamMapper.toResponse(updated);
	}




}
