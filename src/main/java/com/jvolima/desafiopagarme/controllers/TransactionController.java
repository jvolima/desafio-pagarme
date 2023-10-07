package com.jvolima.desafiopagarme.controllers;

import com.jvolima.desafiopagarme.dto.TransactionDTO;
import com.jvolima.desafiopagarme.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Page<TransactionDTO>> listTransactions(Pageable pageable) {
        Page<TransactionDTO> list = transactionService.listTransactions(pageable);

        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<Void> processTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        transactionDTO = transactionService.processTransaction(transactionDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(transactionDTO.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
