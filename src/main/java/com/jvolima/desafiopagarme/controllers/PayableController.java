package com.jvolima.desafiopagarme.controllers;

import com.jvolima.desafiopagarme.dto.BalanceDTO;
import com.jvolima.desafiopagarme.services.PayableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/payables")
public class PayableController {

    @Autowired
    private PayableService payableService;

    @GetMapping(value = "/balance")
    public ResponseEntity<BalanceDTO> getBalance() {
        BalanceDTO balanceDTO = payableService.getBalance();

        return ResponseEntity.ok().body(balanceDTO);
    }
}
