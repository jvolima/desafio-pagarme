package com.jvolima.desafiopagarme.config;

import com.jvolima.desafiopagarme.dto.TransactionDTO;
import com.jvolima.desafiopagarme.entities.Transaction;
import com.jvolima.desafiopagarme.entities.enums.TransactionPaymentMethod;
import com.jvolima.desafiopagarme.repositories.TransactionRepository;
import com.jvolima.desafiopagarme.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Profile("test")
@Configuration
public class IntegrationTestsSeed implements CommandLineRunner {

    @Autowired
    private TransactionService transactionService;

    @Override
    public void run(String... args) throws Exception {
        Date cardExpirationDate;
        String month;
        int year;

        TransactionDTO transactionDTO1 = new TransactionDTO();
        transactionDTO1.setValue(250.0);
        transactionDTO1.setDescription("Gym + fitness products");
        transactionDTO1.setPaymentMethod("credit_card");
        transactionDTO1.setCardNumber("9451 1264 7842 8742");
        transactionDTO1.setCardholderName("John Doe");
        cardExpirationDate = Date.from(Instant.now().plus(6 * 365, ChronoUnit.DAYS));
        month = String.format("%02d", cardExpirationDate.getMonth() + 1);
        year = cardExpirationDate.getYear() + 1900;
        transactionDTO1.setCardExpirationDate(month + "/" + year);
        transactionDTO1.setCvv("874");
        transactionService.processTransaction(transactionDTO1);

        TransactionDTO transactionDTO2 = new TransactionDTO();
        transactionDTO2.setValue(840.0);
        transactionDTO2.setDescription("Nintendo Switch Lite");
        transactionDTO2.setPaymentMethod("debit_card");
        transactionDTO2.setCardNumber("4791 8751 6547 4568");
        transactionDTO2.setCardholderName("Alice Gray");
        cardExpirationDate = Date.from(Instant.now().plus(4 * 400, ChronoUnit.DAYS));
        month = String.format("%02d", cardExpirationDate.getMonth() + 1);
        year = cardExpirationDate.getYear() + 1900;
        transactionDTO2.setCardExpirationDate(month + "/" + year);
        transactionDTO2.setCvv("347");
        transactionService.processTransaction(transactionDTO2);
    }
}
