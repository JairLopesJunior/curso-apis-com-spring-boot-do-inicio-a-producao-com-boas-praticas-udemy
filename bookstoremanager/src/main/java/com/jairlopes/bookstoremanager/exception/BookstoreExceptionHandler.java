package com.jairlopes.bookstoremanager.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class BookstoreExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        return buildResponseEntity(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                Collections.singletonList(ex.getMessage())
        );
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Object> handleEntityExistsException(EntityExistsException ex) {
        return buildResponseEntity(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                Collections.singletonList(ex.getMessage())
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(field -> errors.add("Field " + field.getField().toUpperCase() + " " + field.getDefaultMessage()));
        ex.getBindingResult().getGlobalErrors()
                .forEach(globalError -> errors.add("Object " + globalError.getObjectName().toUpperCase() + " " + globalError.getDefaultMessage()));
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Informed argument(s) validation error(s)", errors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST,
                "Malformed JSON body and/or field error",
                Collections.singletonList(ex.getLocalizedMessage()));
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, String message, List<String> errors) {
        ApiError apiError = ApiError.builder()
                .code(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .message(message)
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(httpStatus).body(apiError);
    }
}













































