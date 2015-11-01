package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.protocol.request.Request;
import com.arturmkrtchyan.mintds.protocol.response.EnumResponse;
import com.arturmkrtchyan.mintds.protocol.response.FailureResponse;
import com.arturmkrtchyan.mintds.protocol.response.NumericResponse;
import com.arturmkrtchyan.mintds.protocol.response.Response;
import com.clearspring.analytics.stream.frequency.CountMinSketch;

import java.time.LocalTime;

public class CountMeanSketchStore extends AbstractKeyValueStore<CountMinSketch> {

    public static final int DEFAULT_WIDTH = 2 << 16;
    public static final int DEFAULT_DEPTH = 4;

    @Override
    public Response add(final Request request) {
        final CountMinSketch sketch = map.get(request.getKey());
        if(sketch != null) {
            sketch.add(request.getValue().get(), 1);
            return EnumResponse.SUCCESS;
        }
        return new FailureResponse("filter doesn't exist.");
    }

    @Override
    public Response count(final Request request) {
        final CountMinSketch sketch = map.get(request.getKey());
        if(sketch != null) {
            return new NumericResponse<>(sketch.estimateCount(request.getValue().get()));
        }
        return new FailureResponse("filter doesn't exist.");
    }

    @Override
    protected CountMinSketch newElement() {
        return new CountMinSketch(DEFAULT_DEPTH, DEFAULT_WIDTH, LocalTime.now().toSecondOfDay());
    }


}
