package com.arturmkrtchyan.mintds.server;

import com.arturmkrtchyan.mintds.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MintDsDaemon {

    private final static Logger logger = LoggerFactory.getLogger(MintDsDaemon.class);

    public static void main(final String[] args) throws IOException {
        Configuration config = Configuration.valueOf(args[0]);

        System.out.println(config);

        final MintDsServer server = new MintDsServer();
        logger.info("Starting MintDS Server.");
        server.start(config.getServerConfig().getBindAddress(),
                config.getServerConfig().getPort());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Stopping MintDS Server.");
            server.stop();
        }));
    }

}
