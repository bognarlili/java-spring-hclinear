package com.hcLinear.backendTest.mapper;

import com.hcLinear.backendTest.dto.team.TeamCreateRequest;
import com.hcLinear.backendTest.dto.team.TeamResponse;
import com.hcLinear.backendTest.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamMapper {


    @Mapping(target = "captainId", source = "captain.id")
    TeamResponse toResponse(Team team);

    List<TeamResponse> toResponses(List<Team> teams);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "captain", ignore = true)
    Team dtoToTeam(TeamCreateRequest teamCreateRequest);

}
