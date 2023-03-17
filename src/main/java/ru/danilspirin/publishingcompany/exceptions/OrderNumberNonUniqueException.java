package ru.danilspirin.publishingcompany.exceptions;

public class OrderNumberNonUniqueException extends IllegalArgumentException{

    public OrderNumberNonUniqueException(){
        super("Номер заказа должен быть уникальным");
    }
}
