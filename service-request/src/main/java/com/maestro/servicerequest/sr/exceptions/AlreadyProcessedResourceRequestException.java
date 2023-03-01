package com.maestro.servicerequest.sr.exceptions;

public class AlreadyProcessedResourceRequestException extends RuntimeException{
    public AlreadyProcessedResourceRequestException(String message) {
        super(message);
    }
}
