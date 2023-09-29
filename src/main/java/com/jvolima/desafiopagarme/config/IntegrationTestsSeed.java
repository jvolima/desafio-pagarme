package com.jvolima.desafiopagarme.config;

import com.jvolima.desafiopagarme.entities.Transaction;
import com.jvolima.desafiopagarme.entities.enums.TransactionPaymentMethod;
import com.jvolima.desafiopagarme.repositories.TransactionRepository;
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
    private TransactionRepository transactionRepository;

    @Override
    public void run(String... args) throws Exception {
        Transaction transaction1 = new Transaction();
        transaction1.setValue(250.0);
        transaction1.setDescription("Gym + fitness products");
        transaction1.setPaymentMethod(TransactionPaymentMethod.credit_card);
        transaction1.setCardNumber("8742");
        transaction1.setCardholderName("John Doe");
        transaction1.setCardExpirationDate(Date.from(Instant.now().plus(5 * 365, ChronoUnit.DAYS)));
        transaction1.setCvv(874);
        transactionRepository.save(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setValue(840.0);
        transaction2.setDescription("Nintendo Switch Lite");
        transaction2.setPaymentMethod(TransactionPaymentMethod.debit_card);
        transaction2.setCardNumber("4568");
        transaction2.setCardholderName("Alice Gray");
        transaction2.setCardExpirationDate(Date.from(Instant.now().plus(3 * 365, ChronoUnit.DAYS)));
        transaction2.setCvv(347);
        transactionRepository.save(transaction2);
    }
}
