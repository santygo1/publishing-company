package ru.danilspirin.publishingcompany.exceptions;

public class IsbnNonUniqueException extends IllegalArgumentException{

    public IsbnNonUniqueException(){
        super("ISBN книги должен быть уникальным.");
    }
}
