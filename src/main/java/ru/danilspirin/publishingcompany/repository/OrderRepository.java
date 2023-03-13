package ru.danilspirin.publishingcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danilspirin.publishingcompany.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
