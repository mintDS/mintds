package com.arturmkrtchyan.mintds.integration;

import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CountMinSktechIT extends AbstractKeyValueStoreIT {

    private List<Pair<String, String>> happyUseCaseData() {
        return Arrays.asList(
            new Pair<>("create countminsketch mysketch", "success"),
            new Pair<>("create countminsketch mysketch", "exists"),
            new Pair<>("exists countminsketch mysketch", "yes"),
            new Pair<>("exists countminsketch newsketch", "no"),
            new Pair<>("add countminsketch mysketch myvalue", "success"),
            new Pair<>("add countminsketch mysketch myvalue", "success"),
            new Pair<>("add countminsketch mysketch myvalue", "success"),
            new Pair<>("count countminsketch mysketch myvalue", "3"),
            new Pair<>("count countminsketch mysketch mynewvalue", "0"),
            new Pair<>("drop countminsketch mysketch", "success"),
            new Pair<>("drop countminsketch mysketch", "non_existent")
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
