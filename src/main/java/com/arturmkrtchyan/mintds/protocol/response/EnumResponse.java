package com.arturmkrtchyan.mintds.protocol.response;

import java.util.Optional;

public enum EnumResponse implements Response {
    SUCCESS,
    EXISTS,
    YES,
    NO,
    NON_EXISTENT;

    public static Optional<Response> fromString(final String response) {
        for (EnumResponse value : EnumResponse.values()) {
            if (value.name().equalsIgnoreCase(response)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}
