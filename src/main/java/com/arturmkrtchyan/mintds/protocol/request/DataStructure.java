package com.arturmkrtchyan.mintds.protocol.request;

import java.util.Optional;

public enum DataStructure {
    BloomFilter,
    HyperLogLog,
    CountMinSketch;

    public static Optional<DataStructure> fromString(final String dataStructure) {
        for (DataStructure ds : DataStructure.values()) {
            if (ds.name().equalsIgnoreCase(dataStructure)) {
                return Optional.of(ds);
            }
        }
        return Optional.empty();
    }
}
