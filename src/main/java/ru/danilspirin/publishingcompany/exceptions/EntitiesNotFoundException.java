package ru.danilspirin.publishingcompany.exceptions;

public class EntitiesNotFoundException extends Exception{

    public EntitiesNotFoundException(Class<?> entityClass){
        super(String.format("Сущности типа %s не найдены", entityClass.getName()));
    }
}
