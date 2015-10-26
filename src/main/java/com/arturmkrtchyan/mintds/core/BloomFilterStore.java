package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.protocol.request.Command;
import com.arturmkrtchyan.mintds.protocol.request.Request;
import com.arturmkrtchyan.mintds.protocol.response.EnumResponse;
import com.arturmkrtchyan.mintds.protocol.response.Response;
import com.clearspring.analytics.stream.membership.BloomFilter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BloomFilterStore implements KeyValueStore {

    public static final int DEFAULT_NUMBER_OF_ELEMENTS = 2 << 25;
    public static final double DEFAULT_FALSE_POSITIVE_PROBABILITY = 1.0;

    private final Map<String, BloomFilter> map = new ConcurrentHashMap<String, BloomFilter>();

    public Response handle(Request request) {
        if(request.getCommand() == Command.CREATE) {
            create(request);
        }
        return EnumResponse.SUCCESS;
    }

    private void create(Request request) {
        map.put(request.getKey(), new BloomFilter(DEFAULT_NUMBER_OF_ELEMENTS, DEFAULT_FALSE_POSITIVE_PROBABILITY));
    }
}
