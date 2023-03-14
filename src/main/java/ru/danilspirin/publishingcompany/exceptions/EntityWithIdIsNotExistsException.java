package ru.danilspirin.publishingcompany.exceptions;

public class EntityWithIdIsNotExistsException extends IllegalArgumentException{
    public EntityWithIdIsNotExistsException(String wrongId, Class<?> entityClass){
        super(String.format(
                "Сущность типа %s с id \"%s\" не существует." +
                        " Укажите id существующей сущности.",   //TODO: i18n
                entityClass.getName(),
                wrongId)
        );
    }
}
