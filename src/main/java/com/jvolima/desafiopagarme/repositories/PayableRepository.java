package com.jvolima.desafiopagarme.repositories;

import com.jvolima.desafiopagarme.entities.Payable;
import com.jvolima.desafiopagarme.entities.enums.PayableStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PayableRepository extends JpaRepository<Payable, UUID> {

    List<Payable> findByStatus(PayableStatus status);
}
