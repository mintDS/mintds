package com.arturmkrtchyan.mintds.integration;

import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class HyperLogLogIT extends AbstractKeyValueStoreIT {

    private List<Pair<String, String>> happyUseCaseData() {
        return Arrays.asList(
                new Pair<>("create hyperloglog mylog", "success"),
                new Pair<>("create hyperloglog mylog", "exists"),
                new Pair<>("exists hyperloglog mylog", "yes"),
                new Pair<>("exists hyperloglog newlog", "no"),
                new Pair<>("add hyperloglog mylog myvalue", "success"),
                new Pair<>("count hyperloglog mylog", "1"),
                new Pair<>("count hyperloglog newlog", "failure filter doesn't exist."),
                new Pair<>("drop hyperloglog mylog", "success"),
                new Pair<>("drop hyperloglog newlog", "non_existent")
        );
    }

    @Test
    public void happyUseCase() throws Exception {
        List<Pair<String, String>> data = happyUseCaseData();
        data.stream().forEach(pair -> {
            String response = client.send(pair.getValue0());
            Assert.assertEquals("Sending request->" + pair.getValue0(),
                    pair.getValue1(), response);
        });
    }

}
