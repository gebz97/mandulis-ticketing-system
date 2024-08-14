package org.mandulis.mts.exception;


import lombok.extern.slf4j.Slf4j;
import org.mandulis.mts.dto.response.ErrorDetails;
import org.mandulis.mts.dto.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.View;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private final View error;

    public GlobalExceptionHandler(View error) {
        this.error = error;
    }


    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<Object> badRequestHandler(UserAlreadyExistsException ex, WebRequest req){
        List<ErrorDetails> errors = new ArrayList<>();
        errors.add(new ErrorDetails(ex.getMessage(), req.getDescription(false), LocalDateTime.now()));
        log.error(ex.getMessage(), errors);
        return ResponseHandler.errorResponse(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> unauthorizedExceptionHandler(BadCredentialsException ex, WebRequest req) {
        List<ErrorDetails> errors = new ArrayList<>();
        errors.add(new ErrorDetails(ex.getMessage(), req.getDescription(false), LocalDateTime.now()));
        log.error(ex.getMessage(), errors);
        return ResponseHandler.errorResponse(errors, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ErrorDetails> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            String field = error.getField();
            errors.add(new ErrorDetails(errorMessage, field, LocalDateTime.now()));
        });
        log.error(ex.getMessage(), errors);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            GroupNotFoundException.class,
            TicketNotFoundException.class
    })
    public ResponseEntity<Object> notFoundExceptionHandler(RuntimeException ex, WebRequest req){
        List<ErrorDetails> errors = new ArrayList<>();
        errors.add(new ErrorDetails(ex.getMessage(), req.getDescription(false), LocalDateTime.now()));
        log.error(ex.getMessage(), errors);
        return ResponseHandler.errorResponse(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> globalExceptionHandler(Exception ex, WebRequest req){
        List<ErrorDetails> errors = new ArrayList<>();
        errors.add(new ErrorDetails(ex.getMessage(), req.getDescription(false), LocalDateTime.now()));
        log.error(ex.getMessage(), errors);
        return ResponseHandler.errorResponse(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
