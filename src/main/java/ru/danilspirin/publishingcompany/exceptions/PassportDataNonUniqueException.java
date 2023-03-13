package ru.danilspirin.publishingcompany.exceptions;

public class PassportDataNonUniqueException extends IllegalArgumentException{


    public PassportDataNonUniqueException(){
        super("Паспортные данные должны быть уникальны.");
    }
}
