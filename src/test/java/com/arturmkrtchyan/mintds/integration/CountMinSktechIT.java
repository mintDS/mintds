package com.arturmkrtchyan.mintds.integration;

import com.arturmkrtchyan.mintds.protocol.request.DefaultRequest;
import com.arturmkrtchyan.mintds.protocol.response.Response;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CountMinSktechIT extends AbstractKeyValueStoreIT {

    private List<Pair<String, Response>> happyUseCaseData() {
        return Arrays.asList(
                new Pair<>("create countminsketch mysketch", Response.fromString("success")),
                new Pair<>("create countminsketch mysketch", Response.fromString("exists")),
                new Pair<>("exists countminsketch mysketch", Response.fromString("yes")),
                new Pair<>("exists countminsketch newsketch", Response.fromString("no")),
                new Pair<>("add countminsketch mysketch myvalue", Response.fromString("success")),
                new Pair<>("add countminsketch mysketch myvalue", Response.fromString("success")),
                new Pair<>("add countminsketch mysketch myvalue", Response.fromString("success")),
                new Pair<>("count countminsketch mysketch myvalue", Response.fromString("3")),
                new Pair<>("count countminsketch mysketch mynewvalue", Response.fromString("0")),
                new Pair<>("drop countminsketch mysketch", Response.fromString("success")),
                new Pair<>("drop countminsketch mysketch", Response.fromString("non_existent"))
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
