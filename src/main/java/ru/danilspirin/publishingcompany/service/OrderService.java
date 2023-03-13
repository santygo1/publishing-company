package ru.danilspirin.publishingcompany.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilspirin.publishingcompany.exceptions.EntityWithIdIsNotExistsException;
import ru.danilspirin.publishingcompany.models.Order;
import ru.danilspirin.publishingcompany.repository.OrderRepository;

import java.util.List;

@RequiredArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class OrderService {
    
    OrderRepository repository;

    @Transactional
    public void create(Order order){
        repository.save(order);
    }

    public List<Order> getAll(){
        return repository.findAll();
    }

    public Order getById(String id){
        return repository.findById(id)
                .orElseThrow(() -> new EntityWithIdIsNotExistsException(id, Order.class));
    }

    @Transactional
    public void update(Order update){
        repository.save(update);
    }

    @Transactional
    public void delete(String id){
        repository.deleteById(id);
    }
}
