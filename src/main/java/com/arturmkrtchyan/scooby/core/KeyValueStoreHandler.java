package com.arturmkrtchyan.scooby.core;

import com.arturmkrtchyan.scooby.protocol.Message;

public class KeyValueStoreHandler {

    private KeyValueStore bloomFilterStore = new BloomFilterStore();


    public void handle(Message message) {
        System.out.println("Handle message!");
    }

}
