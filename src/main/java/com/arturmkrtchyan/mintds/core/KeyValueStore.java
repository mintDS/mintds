package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.protocol.request.Request;
import com.arturmkrtchyan.mintds.protocol.response.EnumResponse;
import com.arturmkrtchyan.mintds.protocol.response.Response;

public interface KeyValueStore {

    default public Response handle(final Request request) {
        switch (request.getCommand()) {
            case CREATE:
                return create(request);
            case EXISTS:
                return exists(request);
            case ADD:
                return add(request);
            case CONTAINS:
                return contains(request);
            case COUNT:
                return count(request);
            case DROP:
                return drop(request);
            default:
                return EnumResponse.SUCCESS;
        }
    }

    Response create(Request request);
    Response exists(Request request);
    Response add(Request request);
    Response contains(Request request);
    Response drop(Request request);
    Response count(Request request);


}
