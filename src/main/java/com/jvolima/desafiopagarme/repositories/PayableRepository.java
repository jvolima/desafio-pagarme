package com.jvolima.desafiopagarme.repositories;

import com.jvolima.desafiopagarme.entities.Payable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PayableRepository extends JpaRepository<Payable, UUID> {
}
