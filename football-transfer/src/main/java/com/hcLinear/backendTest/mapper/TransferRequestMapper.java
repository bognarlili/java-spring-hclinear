package com.hcLinear.backendTest.mapper;

import com.hcLinear.backendTest.dto.transfer.TransferRequestResponse;
import com.hcLinear.backendTest.model.TransferRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransferRequestMapper {

    @Mapping(target = "playerId", source = "player.id")
    @Mapping(target = "fromTeamId", source = "fromTeam.id")
    @Mapping(target = "toTeamId", source = "toTeam.id")
    @Mapping(target = "status", source = "status")
    TransferRequestResponse toResponse(TransferRequest transferRequest);

    List<TransferRequestResponse> toResponses(List<TransferRequest> list);
}
