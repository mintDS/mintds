package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.protocol.Command;
import com.arturmkrtchyan.mintds.protocol.Message;
import com.clearspring.analytics.stream.membership.BloomFilter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BloomFilterStore implements KeyValueStore {

    public static final int DEFAULT_NUMBER_OF_ELEMENTS = 2 << 25;
    public static final double DEFAULT_FALSE_POSITIVE_PROBABILITY = 1.0;

    private final Map<String, BloomFilter> map = new ConcurrentHashMap<String, BloomFilter>();

    public void handle(Message message) {
        if(message.getCommand() == Command.CREATE) {
            create(message);
        }
    }

    private void create(Message message) {
        map.put(message.getKey(), new BloomFilter(DEFAULT_NUMBER_OF_ELEMENTS, DEFAULT_FALSE_POSITIVE_PROBABILITY));
    }
}
