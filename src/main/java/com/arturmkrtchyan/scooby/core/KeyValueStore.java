package com.arturmkrtchyan.scooby.core;

public interface KeyValueStore {



    enum Type {
        BloomFilter,
        LogLog,
        HyperLogLog
    }
}
