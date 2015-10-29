package com.arturmkrtchyan.mintds.protocol.response;

public class FailureResponse implements Response {
    private final String message;

    public FailureResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "failure " + message;
    }
}
