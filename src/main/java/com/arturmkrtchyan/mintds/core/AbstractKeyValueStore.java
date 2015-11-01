package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.protocol.request.Request;
import com.arturmkrtchyan.mintds.protocol.response.EnumResponse;
import com.arturmkrtchyan.mintds.protocol.response.FailureResponse;
import com.arturmkrtchyan.mintds.protocol.response.Response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractKeyValueStore<E> implements KeyValueStore {

    protected final Map<String, E> map = new ConcurrentHashMap<>();

    @Override
    public Response create(final Request request) {
        if(map.containsKey(request.getKey())) {
            return EnumResponse.EXISTS;
        }
        map.put(request.getKey(), newElement());
        return EnumResponse.SUCCESS;
    }

    @Override
    public Response exists(final Request request) {
        return map.containsKey(request.getKey()) ? EnumResponse.YES : EnumResponse.NO;
    }

    @Override
    public Response drop(final Request request) {
        if(!map.containsKey(request.getKey())) {
            return EnumResponse.NON_EXISTENT;
        }
        map.remove(request.getKey());
        return EnumResponse.SUCCESS;
    }

    @Override
    public Response count(Request request) {
        return new FailureResponse("unsupported command");
    }

    @Override
    public Response contains(final Request request) {
        return new FailureResponse("unsupported command");
    }

    protected abstract E newElement();

}
