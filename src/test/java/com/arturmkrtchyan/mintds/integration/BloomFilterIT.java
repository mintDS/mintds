package com.arturmkrtchyan.mintds.integration;

import com.arturmkrtchyan.mintds.client.MintDsCallback;
import org.javatuples.Pair;
import org.junit.*;

import java.util.Arrays;
import java.util.List;

public class BloomFilterIT extends AbstractKeyValueStoreIT {

    private List<Pair<String, String>> happyUseCaseData() {
        return Arrays.asList(
                new Pair<>("create bloomfilter myfilter", "success"),
                new Pair<>("create bloomfilter myfilter", "exists"),
                new Pair<>("exists bloomfilter myfilter", "yes"),
                new Pair<>("exists bloomfilter newfilter", "no"),
                new Pair<>("add bloomfilter myfilter myvalue", "success"),
                new Pair<>("contains bloomfilter myfilter myvalue", "yes"),
                new Pair<>("contains bloomfilter myfilter mynewvalue", "no"),
                new Pair<>("drop bloomfilter myfilter", "success"),
                new Pair<>("drop bloomfilter mynewfilter", "non_existent")
        );
    }

    @Test
    public void happyUseCase() throws Exception {
        List<Pair<String, String>> data = happyUseCaseData();
        data.stream().forEach(pair -> {
            client.send(pair.getValue0(), new MintDsCallback() {
                @Override
                public void onFailure(Throwable throwable) {
                    Assert.fail(throwable.getMessage());
                }

                @Override
                public void onSuccess(String msg) {
                    Assert.assertEquals("Sending request->" + pair.getValue0(),
                            pair.getValue1(), msg);
                }
            });
        });
    }

}
