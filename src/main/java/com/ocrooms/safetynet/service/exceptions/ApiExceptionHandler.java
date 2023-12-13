package com.ocrooms.safetynet.service.exceptions;

import com.ocrooms.safetynet.controller.FireStationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.Objects;

@ControllerAdvice
public class ApiExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(FireStationController.class);

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Object> IllegalArgumentException(RuntimeException ex) {
        ApiException apiException = new ApiException(new Date(), ex.getMessage(), ex.getMessage());
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Object> handleApiRequestException(ItemNotFoundException e) {
        HttpStatus NotFound = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(new Date(), e.getMessage(), NotFound.toString());
        logger.error(e.getMessage());
        return new ResponseEntity<>(apiException, NotFound);
    }

    @ExceptionHandler(ItemAlreadyExists.class)
    public ResponseEntity<Object> ItemAlreadyExists(ItemAlreadyExists e) {
        HttpStatus NotFound = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(new Date(), e.getMessage(), NotFound.toString());
        logger.error(e.getMessage());
        return new ResponseEntity<>(apiException, NotFound);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> customValiationErrorHanding(MethodArgumentNotValidException exception) {
        ApiException apiException = new ApiException(new Date(), "Validation Error", Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage());
        logger.error("Condition @validate non respectée : " + exception.getBindingResult().getFieldError());
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }




}
