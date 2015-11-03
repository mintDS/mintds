package com.arturmkrtchyan.mintds.integration;

import com.arturmkrtchyan.mintds.server.MintDsServer;
import com.arturmkrtchyan.mintds.server.netty.NettyServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AbstractKeyValueStoreIT {

    private static MintDsServer server;
    private static ExecutorService executor;

    @BeforeClass
    public static void setUp() throws Exception {
        System.setProperty("port", "4444");
        executor = Executors.newFixedThreadPool(1);
        server = new NettyServer();
        CompletableFuture.runAsync(server::start, executor);
        Thread.sleep(3000);
    }

    @AfterClass
    public static void tearDown() {
        server.stop();
        executor.shutdown();
    }

}
