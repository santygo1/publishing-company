package ru.danilspirin.publishingcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danilspirin.publishingcompany.models.Contract;

import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {
    Optional<Contract> findByContractNumber(String contractNumber);
}
