package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.protocol.DataStructure;
import com.arturmkrtchyan.mintds.protocol.Message;
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

    public void handle(Message message) {
        logger.debug("Handle message!");
        storeRoutes.get(message.getDataStructure()).handle(message);
    }

}
