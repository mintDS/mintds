package com.arturmkrtchyan.mintds.core;

import com.arturmkrtchyan.mintds.protocol.Message;

public class KeyValueStoreHandler {

    private KeyValueStore bloomFilterStore = new BloomFilterStore();


    public void handle(Message message) {
        System.out.println("Handle message!");
    }

}
