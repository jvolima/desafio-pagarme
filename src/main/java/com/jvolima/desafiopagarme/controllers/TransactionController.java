package com.jvolima.desafiopagarme.controllers;

import com.jvolima.desafiopagarme.controllers.exceptions.StandardError;
import com.jvolima.desafiopagarme.controllers.exceptions.ValidationError;
import com.jvolima.desafiopagarme.dto.TransactionDTO;
import com.jvolima.desafiopagarme.services.TransactionService;
import com.jvolima.desafiopagarme.services.exceptions.UnprocessableEntityException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Transaction", description = "Transaction routes")
@RestController
@RequestMapping(value = "/api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Operation(
            summary = "Get client transactions",
            description = "Show client's transactions as a page, each transaction containing: id, value, description, paymentMethod, cardNumber, cardholderName, cardExpirationDate, cvv and createdAt"
    )
    @ApiResponse(responseCode = "200", description = "Ok", useReturnTypeSchema = true)
    @GetMapping
    public ResponseEntity<Page<TransactionDTO>> listTransactions(
            @ParameterObject Pageable pageable
    ) {
        Page<TransactionDTO> list = transactionService.listTransactions(pageable);

        return ResponseEntity.ok().body(list);
    }

    @Operation(
            summary = "Process client transaction",
            description = "Process client transaction with following parameters: value, description, paymentMethod, cardNumber, cardholderName, cardExpirationDate and cvv"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity", content = { @Content(schema = @Schema(implementation = StandardError.class)) }),
    })
    @PostMapping
    public ResponseEntity<Void> processTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        transactionDTO = transactionService.processTransaction(transactionDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(transactionDTO.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
