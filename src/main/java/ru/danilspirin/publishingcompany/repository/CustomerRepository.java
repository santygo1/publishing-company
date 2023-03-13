package ru.danilspirin.publishingcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danilspirin.publishingcompany.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
