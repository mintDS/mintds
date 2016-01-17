package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.core.datastructure.CountingBloomFilter;
import com.arturmkrtchyan.mintds.protocol.request.Request;
import com.arturmkrtchyan.mintds.protocol.response.EnumResponse;
import com.arturmkrtchyan.mintds.protocol.response.Response;

class CountingBloomFilterStore extends AbstractKeyValueStore<CountingBloomFilter> {

    public CountingBloomFilterStore() {
    }

    @Override
    public void add(final CountingBloomFilter filter, final Request request) {
        filter.add(request.getValue().get());
    }

    @Override
    public Response remove(final CountingBloomFilter filter, final Request request) {
        filter.delete(request.getValue().get());
        return EnumResponse.SUCCESS;
    }

    @Override
    public Response contains(final CountingBloomFilter filter, final Request request) {
        return filter.isPresent(request.getValue().get()) ? EnumResponse.YES : EnumResponse.NO;
    }

    @Override
    protected CountingBloomFilter newElement() {
        return new CountingBloomFilter(1,1);
    }

}
