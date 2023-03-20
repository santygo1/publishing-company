package ru.danilspirin.publishingcompany.requests;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.danilspirin.publishingcompany.models.Customer;
import ru.danilspirin.publishingcompany.models.Order;

/*
    Класс-обертка для запроса, который делегирует Customer и Order

    Используется при создании заказа. Так как order не может существовать
    без заказчика, при создании заказа есть возможность создать заказчика для
    связываемого заказа.
 */
@Getter @Setter @FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCustomerRequest {

    @Valid Customer customer;
    @Valid Order order;

    public OrderCustomerRequest(Customer customer, Order order){
        this.customer = customer;
        this.order = order;
    }
}
