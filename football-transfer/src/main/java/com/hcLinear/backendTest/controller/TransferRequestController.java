package com.hcLinear.backendTest.controller;

import com.hcLinear.backendTest.dto.transfer.TransferRequestCreateRequest;
import com.hcLinear.backendTest.dto.transfer.TransferRequestResponse;
import com.hcLinear.backendTest.mapper.TransferRequestMapper;
import com.hcLinear.backendTest.service.TransferRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers/requests")
public class TransferRequestController {

    private final TransferRequestService transferRequestService;
    private final TransferRequestMapper transferRequestMapper;

    public TransferRequestController(TransferRequestService transferRequestService, TransferRequestMapper transferRequestMapper) {
        this.transferRequestService = transferRequestService;
        this.transferRequestMapper = transferRequestMapper;
    }

    @PostMapping
    public TransferRequestResponse create(@Valid @RequestBody TransferRequestCreateRequest transferCreateRequest) {
        return transferRequestMapper.toResponse(transferRequestService.create(transferCreateRequest));
    }

    @PostMapping("/{id}/reject")
    public void reject(@PathVariable long id) {
        transferRequestService.reject(id);
    }

    @PostMapping("/{id}/accept")
    public void accept(@PathVariable long id) {
        transferRequestService.accept(id);
    }


}
