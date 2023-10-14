package com.jvolima.desafiopagarme.controllers;

import com.jvolima.desafiopagarme.dto.BalanceDTO;
import com.jvolima.desafiopagarme.services.PayableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payable", description = "Payable routes")
@RestController
@RequestMapping(value = "/api/v1/payables")
public class PayableController {

    @Autowired
    private PayableService payableService;

    @Operation(
            summary = "Get client balance",
            description = "Show client available money (items bought in debit) and waiting funds money (items bought in credit)"
    )
    @ApiResponse(responseCode = "200", description = "Ok", useReturnTypeSchema = true)
    @GetMapping(value = "/balance")
    public ResponseEntity<BalanceDTO> getBalance() {
        BalanceDTO balanceDTO = payableService.getBalance();

        return ResponseEntity.ok().body(balanceDTO);
    }
}
