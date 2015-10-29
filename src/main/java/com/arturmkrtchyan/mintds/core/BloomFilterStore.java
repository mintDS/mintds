package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.protocol.request.Request;
import com.arturmkrtchyan.mintds.protocol.response.EnumResponse;
import com.arturmkrtchyan.mintds.protocol.response.FailureResponse;
import com.arturmkrtchyan.mintds.protocol.response.Response;
import com.clearspring.analytics.stream.membership.BloomFilter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BloomFilterStore implements KeyValueStore {

    public static final int DEFAULT_NUMBER_OF_ELEMENTS = 2 << 25;
    public static final double DEFAULT_FALSE_POSITIVE_PROBABILITY = 1.0;

    private final Map<String, BloomFilter> map = new ConcurrentHashMap<String, BloomFilter>();

    public Response handle(final Request request) {
        switch (request.getCommand()) {
            case CREATE:
                return create(request);
            case EXISTS:
                return exists(request);
            case ADD:
                return add(request);
            case CONTAINS:
                return contains(request);
            case DROP:
                return drop(request);
            default:
                return EnumResponse.SUCCESS;
        }

    }

    private Response create(final Request request) {
        if(map.containsKey(request.getKey())) {
            return EnumResponse.EXISTS;
        }
        map.put(request.getKey(), new BloomFilter(DEFAULT_NUMBER_OF_ELEMENTS, DEFAULT_FALSE_POSITIVE_PROBABILITY));
        return EnumResponse.SUCCESS;
    }

    private Response exists(final Request request) {
        return map.containsKey(request.getKey()) ? EnumResponse.YES : EnumResponse.NO;
    }

    private Response add(final Request request) {
        final BloomFilter filter = map.get(request.getKey());
        if(filter != null) {
            filter.add(request.getValue().get());
            return EnumResponse.SUCCESS;
        }
        return new FailureResponse("filter doesn't exist.");
    }

    private Response contains(final Request request) {
        final BloomFilter filter = map.get(request.getKey());
        if(filter != null) {
            return filter.isPresent(request.getValue().get()) ? EnumResponse.YES : EnumResponse.NO;
        }
        return new FailureResponse("filter doesn't exist.");
    }

    private Response drop(final Request request) {
        if(!map.containsKey(request.getKey())) {
            return EnumResponse.NON_EXISTENT;
        }
        map.remove(request.getKey());
        return EnumResponse.SUCCESS;
    }
}
