package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.config.Configuration;
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

    public KeyValueStoreRouter(final Configuration configuration) {
        storeRoutes = new HashMap<>();
        storeRoutes.put(DataStructure.BloomFilter,
                new BloomFilterStore(configuration.getBloomFilterConfig()));
        storeRoutes.put(DataStructure.HyperLogLog, new HyperLogLogStore());
        storeRoutes.put(DataStructure.CountMinSketch, new CountMinSketchStore());
    }

    public Response route(final Request request) {
        logger.debug("Handle request!");
        return storeRoutes.get(request.getDataStructure()).handle(request);
    }

}
