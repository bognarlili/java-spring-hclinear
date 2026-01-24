package com.hcLinear.backendTest.service;

import java.util.List;

import com.hcLinear.backendTest.model.Team;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.hcLinear.backendTest.dto.team.TeamResponse;
import com.hcLinear.backendTest.mapper.TeamMapper;
import com.hcLinear.backendTest.repository.TeamRepository;

@Service
public class TeamService {
	
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    
    public TeamService(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }
    
    
    public List<TeamResponse> findAll() {
        return teamMapper.toResponses(teamRepository.findAll());
    }


    @Transactional
    public Team create(Team team) {
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


}
