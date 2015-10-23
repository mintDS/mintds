package com.arturmkrtchyan.mintds.server;

import com.arturmkrtchyan.mintds.server.netty.NettyServer;

public class MintDsDaemon {

    public static void main(final String[] args) {
        final MintDsServer server = new NettyServer();
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                server.stop();
            }
        });
    }

}
