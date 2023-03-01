package com.maestro.servicerequest.sr.exceptions;

public class ServerNotRespondedException extends RuntimeException{
    public ServerNotRespondedException(String message) {
        super(message);
    }
}
