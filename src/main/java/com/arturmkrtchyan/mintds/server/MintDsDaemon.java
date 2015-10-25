package com.arturmkrtchyan.mintds.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arturmkrtchyan.mintds.server.netty.NettyServer;

public class MintDsDaemon {

    private final static Logger logger = LoggerFactory.getLogger(MintDsDaemon.class);

    public static void main(final String[] args) {
        final MintDsServer server = new NettyServer();
        logger.info("Starting MintDS Server.");
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Stopping MintDS Server.");
            server.stop();
        }));
    }

}
