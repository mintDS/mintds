package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.protocol.request.Request;
import com.arturmkrtchyan.mintds.protocol.response.NumericResponse;
import com.arturmkrtchyan.mintds.protocol.response.Response;
import com.clearspring.analytics.stream.cardinality.HyperLogLog;

class HyperLogLogStore extends AbstractKeyValueStore<HyperLogLog> {

    public static final int DEFAULT_LOG2M = 16;

    public HyperLogLogStore() {
    }

    @Override
    public void add(final HyperLogLog log, final Request request) {
        log.offer(request.getValue().get());
    }

    @Override
    public Response count(final HyperLogLog log, final Request request) {
        return new NumericResponse<>(log.cardinality());
    }

    @Override
    protected HyperLogLog newElement() {
        return new HyperLogLog(DEFAULT_LOG2M);
    }
}
