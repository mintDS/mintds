package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.protocol.request.Request;
import com.arturmkrtchyan.mintds.protocol.response.EnumResponse;
import com.arturmkrtchyan.mintds.protocol.response.FailureResponse;
import com.arturmkrtchyan.mintds.protocol.response.Response;
import com.clearspring.analytics.stream.membership.BloomFilter;

public class BloomFilterStore extends AbstractKeyValueStore<BloomFilter> {

    public static final int DEFAULT_NUMBER_OF_ELEMENTS = 2 << 25;
    public static final double DEFAULT_FALSE_POSITIVE_PROBABILITY = 1.0;

    public BloomFilterStore() {
    }

    @Override
    public void add(final BloomFilter filter, final Request request) {
        filter.add(request.getValue().get());
    }

    @Override
    public Response contains(final BloomFilter filter, final Request request) {
        return filter.isPresent(request.getValue().get()) ? EnumResponse.YES : EnumResponse.NO;
    }

    @Override
    protected BloomFilter newElement() {
        return new BloomFilter(DEFAULT_NUMBER_OF_ELEMENTS, DEFAULT_FALSE_POSITIVE_PROBABILITY);
    }

}
