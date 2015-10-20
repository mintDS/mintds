package com.arturmkrtchyan.mintds.core;

public interface KeyValueStore {



    enum Type {
        BloomFilter,
        LogLog,
        HyperLogLog
    }
}
