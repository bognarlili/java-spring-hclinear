package com.hcLinear.backendTest.repository;

import com.hcLinear.backendTest.model.TransferRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRequestRepository extends JpaRepository<TransferRequest, Long> {
}
