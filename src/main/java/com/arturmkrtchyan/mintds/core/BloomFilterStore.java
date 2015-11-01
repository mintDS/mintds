package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.protocol.request.Request;
import com.arturmkrtchyan.mintds.protocol.response.EnumResponse;
import com.arturmkrtchyan.mintds.protocol.response.FailureResponse;
import com.arturmkrtchyan.mintds.protocol.response.Response;
import com.clearspring.analytics.stream.membership.BloomFilter;

public class BloomFilterStore extends AbstractKeyValueStore<BloomFilter> {

    public static final int DEFAULT_NUMBER_OF_ELEMENTS = 2 << 25;
    public static final double DEFAULT_FALSE_POSITIVE_PROBABILITY = 1.0;

    @Override
    public Response add(final Request request) {
        final BloomFilter filter = map.get(request.getKey());
        if(filter != null) {
            filter.add(request.getValue().get());
            return EnumResponse.SUCCESS;
        }
        return new FailureResponse("filter doesn't exist.");
    }

    @Override
    public Response contains(final Request request) {
        final BloomFilter filter = map.get(request.getKey());
        if(filter != null) {
            return filter.isPresent(request.getValue().get()) ? EnumResponse.YES : EnumResponse.NO;
        }
        return new FailureResponse("filter doesn't exist.");
    }

    @Override
    protected BloomFilter newElement() {
        return new BloomFilter(DEFAULT_NUMBER_OF_ELEMENTS, DEFAULT_FALSE_POSITIVE_PROBABILITY);
    }

}
