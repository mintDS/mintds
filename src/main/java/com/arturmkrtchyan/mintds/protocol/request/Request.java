package com.arturmkrtchyan.mintds.protocol.request;

import java.util.Optional;

public interface Request {

    DataStructure getDataStructure();

    Command getCommand();

    String getKey();

    Optional<String> getValue();
}
