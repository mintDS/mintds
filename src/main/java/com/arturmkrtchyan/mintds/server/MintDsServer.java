package com.arturmkrtchyan.mintds.server;

public interface MintDsServer {

    int PORT = Integer.parseInt(System.getProperty("port", "7657"));

    void start();

    void stop();

}
