package com.hcLinear.backendTest.mapper;

import java.util.List;

import com.hcLinear.backendTest.dto.team.TeamCreateRequest;
import com.hcLinear.backendTest.dto.team.TeamListResponse;
import org.mapstruct.Mapper;

import com.hcLinear.backendTest.dto.team.TeamResponse;
import com.hcLinear.backendTest.model.Team;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamMapper {


    @Mapping(target = "captainId", source = "captain.id")
    TeamResponse toResponse(Team team);

    List<TeamResponse> toResponses(List<Team> teams);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "captain", ignore = true)
    Team dtoToTeam(TeamCreateRequest teamCreateRequest);

}
