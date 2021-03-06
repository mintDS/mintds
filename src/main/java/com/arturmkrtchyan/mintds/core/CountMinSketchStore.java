package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.protocol.request.Request;
import com.arturmkrtchyan.mintds.protocol.response.NumericResponse;
import com.arturmkrtchyan.mintds.protocol.response.Response;
import com.clearspring.analytics.stream.frequency.CountMinSketch;

import java.time.LocalTime;

class CountMinSketchStore extends AbstractKeyValueStore<CountMinSketch> {

    public static final int DEFAULT_WIDTH = 16_777_216; // 2^24
    public static final int DEFAULT_DEPTH = 4;

    public CountMinSketchStore() {
    }

    @Override
    public void add(final CountMinSketch sketch, final Request request) {
        sketch.add(request.getValue().get(), 1);
    }

    @Override
    public Response count(final CountMinSketch sketch, final Request request) {
        return new NumericResponse<>(sketch.estimateCount(request.getValue().get()));
    }

    @Override
    protected CountMinSketch newElement() {
        return new CountMinSketch(DEFAULT_DEPTH, DEFAULT_WIDTH, LocalTime.now().toSecondOfDay());
    }

}
