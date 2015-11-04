package com.arturmkrtchyan.mintds.integration;

import com.arturmkrtchyan.mintds.cli.MintDsClient;
import com.arturmkrtchyan.mintds.server.MintDsServer;
import com.arturmkrtchyan.mintds.server.netty.NettyServer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AbstractKeyValueStoreIT {

    private static MintDsServer server;
    private static ExecutorService executor;

    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 4444;

    protected MintDsClient client = new MintDsClient(Optional.of(DEFAULT_HOST), Optional.of(DEFAULT_PORT));

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.setProperty("port", String.valueOf(DEFAULT_PORT));
        executor = Executors.newFixedThreadPool(1);
        server = new NettyServer();
        CompletableFuture.runAsync(server::start, executor);
        Thread.sleep(3000);
    }

    @AfterClass
    public static void afterClass() {
        server.stop();
        executor.shutdown();
    }


    @Before
    public void beforeMethod() throws Exception {
        client.connect();
    }

    @After
    public void afterMethod() throws Exception {
        client.disconnect();
    }

}
