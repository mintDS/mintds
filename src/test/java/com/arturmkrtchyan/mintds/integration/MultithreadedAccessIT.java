package com.arturmkrtchyan.mintds.integration;

import com.arturmkrtchyan.mintds.client.MintDsClient;
import com.arturmkrtchyan.mintds.protocol.response.Response;
import org.javatuples.Pair;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MultithreadedAccessIT extends AbstractKeyValueStoreIT {

    private static MintDsClient multithreadedClient;

    @BeforeClass
    public static void beforeClass() throws Exception {
        startServer();
        multithreadedClient = new MintDsClient.Builder()
                .host(DEFAULT_HOST)
                .port(DEFAULT_PORT)
                .numberOfThreads(4)
                .numberOfConnections(32)
                .build();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        multithreadedClient.close();
        stopServer();
    }

    private List<Pair<String, Response>> happyUseCaseData() {
        return Arrays.asList(
                new Pair<>("create bloomfilter myfilter", Response.fromString("success")),
                new Pair<>("create bloomfilter myfilter", Response.fromString("exists")),
                new Pair<>("exists bloomfilter myfilter", Response.fromString("yes")),
                new Pair<>("exists bloomfilter newfilter", Response.fromString("no")),
                new Pair<>("add bloomfilter myfilter myvalue", Response.fromString("success")),
                new Pair<>("contains bloomfilter myfilter myvalue", Response.fromString("yes")),
                new Pair<>("contains bloomfilter myfilter mynewvalue", Response.fromString("no")),
                new Pair<>("drop bloomfilter myfilter", Response.fromString("success")),
                new Pair<>("drop bloomfilter mynewfilter", Response.fromString("non_existent"))
        );
    }

    @Test
    public void happyUseCase() throws Exception {
        List<Pair<String, Response>> data = happyUseCaseData();
        data.stream().forEach(pair -> {
            CompletableFuture<Response> future = multithreadedClient.send(pair.getValue0());
            try {
                Assert.assertEquals("Sending request->" + pair.getValue0(),
                        pair.getValue1(), future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
