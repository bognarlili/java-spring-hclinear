package com.hcLinear.backendTest.mapper;

import com.hcLinear.backendTest.dto.player.PlayerCreateRequest;
import com.hcLinear.backendTest.dto.player.PlayerResponse;
import com.hcLinear.backendTest.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", ignore = true)
    Player dtoToPlayer(PlayerCreateRequest req);

    @Mapping(target = "fullName", expression = "java(player.getFirstName() + \" \" + player.getLastName())")
    @Mapping(target = "teamName", expression = "java(player.getTeam() == null ? null : player.getTeam().getName())")
    PlayerResponse toResponse(Player player);

    List<PlayerResponse> toResponses(List<Player> players);
}
