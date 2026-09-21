package com.devopshub.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // Tells Spring this is the Global Customer Service Desk
public class GlobalExceptionHandler {

    // Tells Spring: "Listen for ANY validation errors in the app and send them
    // here!"
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        // Loop through the ugly error text, extract the field name and our custom
        // message, and put it in a clean Map
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    // @ResponseStatus and @ExceptionHandler are BUILT-IN Spring annotations.
    // ResourceNotFoundException is CUSTOM (we just created it).
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, String> handleResourceNotFound(ResourceNotFoundException ex) {

        // HashMap and Map are BUILT-IN Java tools.
        Map<String, String> error = new HashMap<>();

        // ex.getMessage() is a BUILT-IN Java method that gets the string we wrote in
        // the Service ("Project not found with id: 999")
        error.put("error", ex.getMessage());

        return error;
    }
}
