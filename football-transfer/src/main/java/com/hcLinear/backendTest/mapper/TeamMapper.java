package com.hcLinear.backendTest.mapper;

import java.util.List;

import com.hcLinear.backendTest.dto.team.TeamCreateRequest;
import org.mapstruct.Mapper;

import com.hcLinear.backendTest.dto.team.TeamResponse;
import com.hcLinear.backendTest.model.Team;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    @Mapping(target = "playerCount", ignore = true)
    @Mapping(target = "captainFullName", ignore = true)
    TeamResponse toResponse(Team team);

    @Mapping(target = "id", ignore = true)
    Team dtoToTeam(TeamCreateRequest teamCreateRequest);


    List<TeamResponse> toResponses(List<Team> teams);

}
