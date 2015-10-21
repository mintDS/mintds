package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.protocol.Message;

public interface KeyValueStore {

    void handle(Message message);

    enum Type {
        BloomFilter,
        LogLog,
        HyperLogLog
    }
}
