package com.arturmkrtchyan.mintds.protocol.response;

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
}
