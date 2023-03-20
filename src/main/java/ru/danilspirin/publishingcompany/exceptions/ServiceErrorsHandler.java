package ru.danilspirin.publishingcompany.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceErrorsHandler {

    @ExceptionHandler(BookServiceError.class)
    String bookServiceErrorHandler(){
        return "errors/order-service-error";
    }
}
