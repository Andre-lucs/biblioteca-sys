package com.andrelucs.bibliotecasys.resources.exception;

import com.andrelucs.bibliotecasys.services.exception.BadRequestException;
import com.andrelucs.bibliotecasys.services.exception.InvalidAcessException;
import com.andrelucs.bibliotecasys.services.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> badRequest(BadRequestException e, HttpServletRequest request){
        String error = "Bad Request";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI() );
        return ResponseEntity.status(status).body(err);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardError> notFound(NotFoundException e, HttpServletRequest request){
        String error = "Object not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI() );
        return ResponseEntity.status(status).body(err);
    }
    @ExceptionHandler(InvalidAcessException.class)
    public ResponseEntity<StandardError> invalidAcess(InvalidAcessException e, HttpServletRequest request){
        String error = "Invalid Login credentials";
        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI() );
        return ResponseEntity.status(status).body(err);
    }
    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<StandardError> dataBaseIntegrity(PSQLException e, HttpServletRequest request){
        String error = "Database integrity violation";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI() );
        return ResponseEntity.status(status).body(err);
    }
}
