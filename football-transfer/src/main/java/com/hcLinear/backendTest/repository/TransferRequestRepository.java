package com.hcLinear.backendTest.repository;

import com.hcLinear.backendTest.model.TransferRequest;
import com.hcLinear.backendTest.model.TransferRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TransferRequestRepository extends JpaRepository<TransferRequest, Long>, JpaSpecificationExecutor<TransferRequest> {

    List<TransferRequest> findByPlayerIdAndStatus(Long playerId, TransferRequestStatus status);

}
