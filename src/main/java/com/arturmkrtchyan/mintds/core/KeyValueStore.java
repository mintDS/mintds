package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.protocol.request.Request;
import com.arturmkrtchyan.mintds.protocol.response.Response;

public interface KeyValueStore {

    Response handle(Request request);

    enum Type {
        BloomFilter,
        LogLog,
        HyperLogLog
    }
}
