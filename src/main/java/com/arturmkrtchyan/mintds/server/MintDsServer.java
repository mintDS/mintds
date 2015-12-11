package com.arturmkrtchyan.mintds.server;

import com.arturmkrtchyan.mintds.server.netty.NettyServer;

public class MintDsServer {

    public static final int PORT = Integer.parseInt(System.getProperty("port", "7657"));

    private final NettyServer server;

    public MintDsServer() {
        server = new NettyServer();
    }


    public void start() {
        server.start(PORT);
    }

    public void stop() {
        server.stop();
    }

}
