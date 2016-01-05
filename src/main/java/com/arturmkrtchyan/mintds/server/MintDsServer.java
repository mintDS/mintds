package com.arturmkrtchyan.mintds.server;

import com.arturmkrtchyan.mintds.config.Configuration;
import com.arturmkrtchyan.mintds.server.netty.NettyServer;

public class MintDsServer {

    private final NettyServer server;

    public MintDsServer() {
        server = new NettyServer();
    }

    public void start(final Configuration configuration) {
        server.start(configuration);
    }

    public void stop() {
        server.stop();
    }

}
