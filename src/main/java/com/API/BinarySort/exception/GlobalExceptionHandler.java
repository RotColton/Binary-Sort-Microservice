package com.API.BinarySort.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebInputException;

import static com.API.BinarySort.exception.ErrorMessages.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }
    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<String> handleServerWebInputException(ServerWebInputException ex) {
        return ResponseEntity.badRequest().body(INVALID_INPUT_DATA);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        return ResponseEntity.badRequest().body(NULL_DATA);
    }
}
