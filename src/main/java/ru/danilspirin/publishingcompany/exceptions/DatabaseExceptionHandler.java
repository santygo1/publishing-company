package ru.danilspirin.publishingcompany.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class DatabaseExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(EntityWithIdIsNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String entityWithIdIsNotExistsHandler(EntityWithIdIsNotExistsException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(EntitiesNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    String entitiesNotFoundExceptionHandler(EntitiesNotFoundException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String entityAlreadyExistsHandler(EntityAlreadyExistsException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(PassportDataNonUniqueException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String passportDataNonUniqueHandler(PassportDataNonUniqueException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(IsbnNonUniqueException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String isbnNonUniqueHandler(IsbnNonUniqueException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ContractNumberNonUniqueException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String contractNumberNonUniqueHandler(ContractNumberNonUniqueException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(OrderNumberNonUniqueException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String orderNumberNonUniqueHandler(OrderNumberNonUniqueException ex){
        return ex.getMessage();
    }
}
