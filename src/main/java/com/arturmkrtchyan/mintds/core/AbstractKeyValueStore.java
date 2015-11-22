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
        if (map.containsKey(request.getKey())) {
            return EnumResponse.EXISTS;
        }
        map.put(request.getKey(), newElement());
        return EnumResponse.SUCCESS;
    }

    @Override
    public Response add(final Request request) {
        final E element = map.get(request.getKey());
        if (element != null) {
            add(element, request);
            return EnumResponse.SUCCESS;
        }
        return new FailureResponse("Failure filter doesn't exist.");
    }

    @Override
    public Response exists(final Request request) {
        return map.containsKey(request.getKey()) ? EnumResponse.YES : EnumResponse.NO;
    }

    @Override
    public Response drop(final Request request) {
        if (!map.containsKey(request.getKey())) {
            return EnumResponse.NON_EXISTENT;
        }
        map.remove(request.getKey());
        return EnumResponse.SUCCESS;
    }

    @Override
    public Response count(final Request request) {
        final E element = map.get(request.getKey());
        if (element != null) {
            return count(element, request);
        }
        return new FailureResponse("Failure filter doesn't exist.");
    }

    @Override
    public Response contains(final Request request) {
        final E element = map.get(request.getKey());
        if (element != null) {
            return contains(element, request);
        }
        return new FailureResponse("Failure filter doesn't exist.");
    }


    protected Response count(final E element, final Request request) {
        return new FailureResponse("Failure unsupported command");
    }

    protected Response contains(final E element, final Request request) {
        return new FailureResponse("Failure unsupported command");
    }

    protected abstract E newElement();

    protected abstract void add(E element, Request request);

}
