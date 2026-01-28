package com.hcLinear.backendTest.service;

import com.hcLinear.backendTest.model.*;
import org.springframework.data.jpa.domain.Specification;

public class TransferRequestSpecification {

    public static Specification<TransferRequest> hasPlayerId(Long playerId) {
        return (root, cq, cb) -> {
            if (playerId == null) return cb.conjunction();
            return cb.equal(root.get(TransferRequest_.player).get(Player_.id), playerId);
        };
    }

    public static Specification<TransferRequest> hasFromTeamId(Long fromTeamId) {
        return (root, cq, cb) -> {
            if (fromTeamId == null) return cb.conjunction();
            return  cb.equal(root.get(TransferRequest_.fromTeam).get(Team_.id), fromTeamId);
        };
    }

    public static Specification<TransferRequest> hasToTeamId(Long toTeamId) {
        return (root, cq, cb) ->{
            if (toTeamId == null) return cb.conjunction();
            return cb.equal(root.get(TransferRequest_.toTeam).get(Team_.id), toTeamId);
        };
    }

    public static Specification<TransferRequest> hasStatus(TransferRequestStatus status) {
        return (root, cq, cb) -> {
            if (status == null) return cb.conjunction();
            return cb.equal(root.get(TransferRequest_.status), status);
        };

    }

}
