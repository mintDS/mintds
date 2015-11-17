package com.arturmkrtchyan.mintds.protocol.response;

import java.util.Optional;

public class NumericResponse<T extends Number> implements Response {
    private T value;


    public NumericResponse(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public static Optional<Response> fromString(final String response) {
        try {
            return Optional.of(new NumericResponse<>(Integer.parseInt(response)));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
