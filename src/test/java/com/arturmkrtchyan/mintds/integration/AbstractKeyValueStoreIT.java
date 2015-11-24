package com.arturmkrtchyan.mintds.integration;

import com.arturmkrtchyan.mintds.client.MintDsClient;
import com.arturmkrtchyan.mintds.server.MintDsServer;
import com.arturmkrtchyan.mintds.server.netty.NettyServer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AbstractKeyValueStoreIT {

    private static MintDsServer server;
    protected static MintDsClient client;
    private static ExecutorService executor;

    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 4444;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.setProperty("port", String.valueOf(DEFAULT_PORT));
        executor = Executors.newFixedThreadPool(1);
        server = new NettyServer();
        CompletableFuture.runAsync(server::start, executor);
        Thread.sleep(3000);

        client = new MintDsClient.Builder()
                .host(DEFAULT_HOST)
                .port(DEFAULT_PORT)
                .numberOfThreads(1)
                .numberOfConnections(1)
                .build();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        client.close();
        server.stop();
        executor.shutdown();
    }

}
