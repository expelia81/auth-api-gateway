package com.maestro.servicerequest.sr.exceptions;

public class NotRequestedService extends RuntimeException{
    public NotRequestedService(String message) {
        super(message);
    }
}
