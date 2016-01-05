package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.config.BloomFilterConfig;
import com.arturmkrtchyan.mintds.protocol.request.Request;
import com.arturmkrtchyan.mintds.protocol.response.EnumResponse;
import com.arturmkrtchyan.mintds.protocol.response.Response;
import com.clearspring.analytics.stream.membership.BloomFilter;

class BloomFilterStore extends AbstractKeyValueStore<BloomFilter> {

    private final BloomFilterConfig config;

    public BloomFilterStore(final BloomFilterConfig config) {
        this.config = config;
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
        return new BloomFilter(config.getElements(), config.getProbability());
    }

}
