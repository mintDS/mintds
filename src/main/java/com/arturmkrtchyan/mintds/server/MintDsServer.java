package com.arturmkrtchyan.mintds.server;

import com.arturmkrtchyan.mintds.server.netty.NettyServer;

public class MintDsServer {

    private final NettyServer server;

    public MintDsServer() {
        server = new NettyServer();
    }


    public void start(final String bindAddress, final int port) {
        server.start(bindAddress, port);
    }

    public void stop() {
        server.stop();
    }

}
