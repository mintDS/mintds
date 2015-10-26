package com.arturmkrtchyan.mintds.protocol.request;

public interface Request {

    DataStructure getDataStructure();

    Command getCommand();

    String getKey();

    String getValue();
}
