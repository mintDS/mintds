package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.protocol.DataStructure;
import com.arturmkrtchyan.mintds.protocol.Message;

import java.util.HashMap;
import java.util.Map;

public final class KeyValueStoreRouter {

    private static KeyValueStoreRouter router = new KeyValueStoreRouter();

    private final Map<DataStructure, KeyValueStore> storeRoutes;

    private KeyValueStoreRouter() {
        storeRoutes = new HashMap<DataStructure, KeyValueStore>();
        storeRoutes.put(DataStructure.BloomFilter, new BloomFilterStore());
    }

    public static KeyValueStoreRouter getInstance() {
        return router;
    }

    public void handle(Message message) {
        System.out.println("Handle message!");
        storeRoutes.get(message.getDataStructure()).handle(message);
    }

}
