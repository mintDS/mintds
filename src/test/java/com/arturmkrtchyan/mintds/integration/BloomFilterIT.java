package com.arturmkrtchyan.mintds.integration;

import com.arturmkrtchyan.mintds.protocol.request.DefaultRequest;
import com.arturmkrtchyan.mintds.protocol.response.Response;
import org.javatuples.Pair;
import org.junit.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BloomFilterIT extends AbstractKeyValueStoreIT {

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
                new Pair<>("drop bloomfilter mynewfilter", Response.fromString("non_existent")),
                new Pair<>("contains bloomfilter nofilter myvalue",
                        Response.fromString("failure nofilter doesn't exist."))
        );
    }

    @Test
    public void happyUseCase() throws Exception {
        List<Pair<String, Response>> data = happyUseCaseData();
        data.stream().forEach(pair -> {
            CompletableFuture<Response> future = client.send(DefaultRequest.fromString(pair.getValue0()));
            try {
                Assert.assertEquals("Sending request->" + pair.getValue0(),
                        pair.getValue1(), future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
