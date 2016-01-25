package com.arturmkrtchyan.mintds.integration;

import com.arturmkrtchyan.mintds.protocol.request.DefaultRequest;
import com.arturmkrtchyan.mintds.protocol.response.Response;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CountingBloomFilterIT extends AbstractKeyValueStoreIT {

    private List<Pair<String, Response>> happyUseCaseData() {
        return Arrays.asList(
                new Pair<>("create countingbloomfilter myfilter", Response.fromString("success")),
                new Pair<>("create countingbloomfilter myfilter", Response.fromString("exists")),
                new Pair<>("exists countingbloomfilter myfilter", Response.fromString("yes")),
                new Pair<>("exists countingbloomfilter newfilter", Response.fromString("no")),
                new Pair<>("add countingbloomfilter myfilter myvalue", Response.fromString("success")),
                new Pair<>("contains countingbloomfilter myfilter myvalue", Response.fromString("yes")),
                new Pair<>("contains countingbloomfilter myfilter mynewvalue", Response.fromString("no")),
                new Pair<>("count countingbloomfilter myfilter myvalue", Response.fromString("1")),
                new Pair<>("add countingbloomfilter myfilter myvalue", Response.fromString("success")),
                new Pair<>("count countingbloomfilter myfilter myvalue", Response.fromString("2")),
                new Pair<>("remove countingbloomfilter myfilter myvalue", Response.fromString("success")),
                new Pair<>("count countingbloomfilter myfilter myvalue", Response.fromString("1")),
                new Pair<>("drop countingbloomfilter myfilter", Response.fromString("success")),
                new Pair<>("drop countingbloomfilter mynewfilter", Response.fromString("non_existent")),
                new Pair<>("contains countingbloomfilter nofilter myvalue",
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
