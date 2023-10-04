package com.andrelucs.bibliotecasys.services.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String reason, String table){
        super("Request input is invalid. Reason : "+reason+". Table : "+ table);
    }

    public BadRequestException(String reason) {
        super("Request input is invalid. Reason : "+reason);
    }
}
