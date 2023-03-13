package ru.danilspirin.publishingcompany.exceptions;

public class ContractNumberNonUniqueException extends RuntimeException{

    public ContractNumberNonUniqueException(){
        super("Номер контракта должен быть уникальным");
    }
}
