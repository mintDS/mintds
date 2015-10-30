package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.protocol.request.Request;
import com.arturmkrtchyan.mintds.protocol.response.EnumResponse;
import com.arturmkrtchyan.mintds.protocol.response.FailureResponse;
import com.arturmkrtchyan.mintds.protocol.response.NumericResponse;
import com.arturmkrtchyan.mintds.protocol.response.Response;
import com.clearspring.analytics.stream.frequency.CountMinSketch;

import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CountMeanSketchStore implements KeyValueStore {

    public static final int DEFAULT_WIDTH = 2 << 16;
    public static final int DEFAULT_DEPTH = 4;

    private final Map<String, CountMinSketch> map = new ConcurrentHashMap<>();

    public Response handle(final Request request) {
        switch (request.getCommand()) {
            case CREATE:
                return create(request);
            case EXISTS:
                return exists(request);
            case ADD:
                return add(request);
            case COUNT:
                return count(request);
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
        map.put(request.getKey(), new CountMinSketch(DEFAULT_DEPTH, DEFAULT_WIDTH, LocalTime.now().toSecondOfDay()));
        return EnumResponse.SUCCESS;
    }

    private Response exists(final Request request) {
        return map.containsKey(request.getKey()) ? EnumResponse.YES : EnumResponse.NO;
    }

    private Response add(final Request request) {
        final CountMinSketch sketch = map.get(request.getKey());
        if(sketch != null) {
            sketch.add(request.getValue().get(), 1);
            return EnumResponse.SUCCESS;
        }
        return new FailureResponse("filter doesn't exist.");
    }

    private Response count(final Request request) {
        final CountMinSketch sketch = map.get(request.getKey());
        if(sketch != null) {
            return new NumericResponse<>(sketch.estimateCount(request.getValue().get()));
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
