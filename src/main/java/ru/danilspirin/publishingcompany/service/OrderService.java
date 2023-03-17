package ru.danilspirin.publishingcompany.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilspirin.publishingcompany.exceptions.EntityWithIdIsNotExistsException;
import ru.danilspirin.publishingcompany.models.Order;
import ru.danilspirin.publishingcompany.repository.OrderRepository;

import java.util.List;

@RequiredArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
public class OrderService {
    
    OrderRepository orderRepository;

    @Transactional
    public Order addOrder(Order order){
        orderRepository.findById(order.getId())
                        .ifPresent(orderDB -> {
                            throw new EntityWithIdIsNotExistsException(order.getId(), Order.class);
                        });
        return orderRepository.save(order);
    }

    public List<Order> getAll(){
        return orderRepository.findAll();
    }

    public Order getOrder(String id){
        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(id, Order.class)
                );
    }

    @Transactional
    public void deleteOrder(String id){
        orderRepository.deleteById(id);
    }
}
