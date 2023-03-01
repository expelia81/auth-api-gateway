package com.maestro.servicerequest.sr.submit.domain;

public enum ResourceStatusCode {
    REQUESTED(1, "Requested"),
    IN_PROGRESS(2, "In Progress"),
    COMPLETED(3, "Completed"),
    FAILED(4, "Failed"),
    REJECTED(5, "Rejected"),

    NOT_RESPONDED(6, "Not Responded");


    public int code;

    String name;

    ResourceStatusCode(int i, String status) {
        this.code = i;
        this.name = status;
    }
}
