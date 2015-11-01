package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.protocol.request.Request;
import com.arturmkrtchyan.mintds.protocol.response.EnumResponse;
import com.arturmkrtchyan.mintds.protocol.response.FailureResponse;
import com.arturmkrtchyan.mintds.protocol.response.NumericResponse;
import com.arturmkrtchyan.mintds.protocol.response.Response;
import com.clearspring.analytics.stream.cardinality.HyperLogLog;

public class HyperLogLogStore extends AbstractKeyValueStore<HyperLogLog> {

    public static final int DEFAULT_LOG2M = 16;

    public Response add(final Request request) {
        final HyperLogLog log = map.get(request.getKey());
        if(log != null) {
            log.offer(request.getValue().get());
            return EnumResponse.SUCCESS;
        }
        return new FailureResponse("filter doesn't exist.");
    }

    public Response count(final Request request) {
        final HyperLogLog log = map.get(request.getKey());
        if(log != null) {
            return new NumericResponse<>(log.cardinality());
        }
        return new FailureResponse("filter doesn't exist.");
    }

    @Override
    protected HyperLogLog newElement() {
        return new HyperLogLog(DEFAULT_LOG2M);
    }
}
