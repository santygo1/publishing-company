package ru.danilspirin.publishingcompany.exceptions;

public class EntityAlreadyExistsException extends IllegalArgumentException{
    public EntityAlreadyExistsException(String id, Class<?> entityClass){
        super(String.format("Сущность типа %s с id %s уже существует. " +
                "Укажите id не существующей сущности", //TODO: i18n
                entityClass.getName(),
                id));
    }
}
