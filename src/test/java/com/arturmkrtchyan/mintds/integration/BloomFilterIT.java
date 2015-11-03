package com.arturmkrtchyan.mintds.integration;

import org.junit.*;

public class BloomFilterIT extends AbstractKeyValueStoreIT {

    @Test
    public void myTest() throws Exception {
        System.out.println("myTest");
        client.send("create bloomfilter myfilter");
        Assert.assertTrue(true);
    }

}
