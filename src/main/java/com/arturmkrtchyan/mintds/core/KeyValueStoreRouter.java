package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.protocol.request.DataStructure;
import com.arturmkrtchyan.mintds.protocol.request.Request;
import com.arturmkrtchyan.mintds.protocol.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class KeyValueStoreRouter {

    private final static Logger logger = LoggerFactory.getLogger(KeyValueStoreRouter.class);

    private final Map<DataStructure, KeyValueStore> storeRoutes;

    public KeyValueStoreRouter() {
        storeRoutes = new HashMap<>();
        storeRoutes.put(DataStructure.BloomFilter, new BloomFilterStore());
    }

    public Response handle(Request request) {
        logger.debug("Handle request!");
        return storeRoutes.get(request.getDataStructure()).handle(request);
    }

}
