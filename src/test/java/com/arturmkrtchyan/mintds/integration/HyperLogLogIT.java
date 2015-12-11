package com.arturmkrtchyan.mintds.integration;

import com.arturmkrtchyan.mintds.protocol.request.DefaultRequest;
import com.arturmkrtchyan.mintds.protocol.response.Response;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HyperLogLogIT extends AbstractKeyValueStoreIT {

    private List<Pair<String, Response>> happyUseCaseData() {
        return Arrays.asList(
                new Pair<>("create hyperloglog mylog", Response.fromString("success")),
                new Pair<>("create hyperloglog mylog", Response.fromString("exists")),
                new Pair<>("exists hyperloglog mylog", Response.fromString("yes")),
                new Pair<>("exists hyperloglog newlog", Response.fromString("no")),
                new Pair<>("add hyperloglog mylog myvalue", Response.fromString("success")),
                new Pair<>("count hyperloglog mylog", Response.fromString("1")),
                new Pair<>("drop hyperloglog mylog", Response.fromString("success")),
                new Pair<>("drop hyperloglog newlog", Response.fromString("non_existent")),
                new Pair<>("count hyperloglog nolog", Response.fromString("failure nolog doesn't exist."))
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
