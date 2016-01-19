package com.arturmkrtchyan.mintds.core.datastructure;


import com.clearspring.analytics.hash.MurmurHash;

import java.io.UnsupportedEncodingException;

public class CountingBloomFilter {

    // Storage
    private long[] buckets;

    // 4bit buckets, bucket can count to 15
    private final static long BUCKET_MAX_COUNT = 15;

    private final int numElements;

    private int hashCount = 2; // TODO calculate

    public CountingBloomFilter(final int numElements, final double maxFalsePosProbability) {
        this.numElements = numElements;
        buckets = new long[calculateBucketSize(numElements)];
        System.out.println(buckets.length);
    }

    // how many longs (64 bits) would take to hold numElements buckets (4 bits)
    private int calculateBucketSize(final int numElements) {
        return ((numElements - 1) >>> 4) + 1;
    }

    public boolean isPresent(String key) {
        int[] hashes = getHashBuckets(key, hashCount, numElements);
        for(int hash : hashes) {
            // find the bucket
            int bucketIndex = hash >> 4; // div 16 ; to find the index inside buckets
            System.out.println("hash: " + hash);
            System.out.println("index: " + bucketIndex);
            int bucketShift = (hash & 0x0f) << 2;  // (mod 16) * 4 ; index inside a single long
            System.out.println("shift: " + bucketShift);

            long bucketMask = 15L << bucketShift;
            System.out.println("mask: " + bucketMask);
            System.out.println("exists: " + (buckets[bucketIndex] & bucketMask));

            if((buckets[bucketIndex] & bucketMask) == 0) {
                return false;
            }
        }
        return true;
    }

    public void add(String key) {
        int[] hashes = getHashBuckets(key, hashCount, numElements);
        for(int hash : hashes) {
            // find the bucket
            int bucketIndex = hash >> 4; // div 16 ; to find the index inside buckets
            int bucketShift = (hash & 0x0f) << 2;  // (mod 16) * 4 ; index inside a single long

            long bucketMask = 15L << bucketShift;
            long bucketValue = (buckets[bucketIndex] & bucketMask) >>> bucketShift;

            // only increment if the count in the bucket is less than BUCKET_MAX_COUNT
            if(bucketValue < BUCKET_MAX_COUNT) {
                // increment by 1
                buckets[bucketIndex] = (buckets[bucketIndex] & ~bucketMask) | ((bucketValue + 1) << bucketShift);
            }
        }
    }

    public void delete(String key) {
        int[] hashes = getHashBuckets(key, hashCount, numElements);
        for(int hash : hashes) {
            // find the bucket
            int bucketIndex = hash >> 4; // div 16 ; to find the index inside buckets
            int bucketShift = (hash & 0x0f) << 2;  // (mod 16) * 4 ; index inside a single long

            long bucketMask = 15L << bucketShift;
            long bucketValue = (buckets[bucketIndex] & bucketMask) >>> bucketShift;

            // only decrement if the count in the bucket is between 0 and BUCKET_MAX_COUNT
            if(bucketValue >= 1 && bucketValue < BUCKET_MAX_COUNT) {
                // increment by 1
                buckets[bucketIndex] = (buckets[bucketIndex] & ~bucketMask) | ((bucketValue - 1) << bucketShift);
            }
        }
    }

    public int count(String key) {
        int res = Integer.MAX_VALUE;
        int[] hashes = getHashBuckets(key, hashCount, numElements);
        for(int hash : hashes) {
            // find the bucket
            int bucketIndex = hash >> 4; // div 16 ; to find the index inside buckets
            int bucketShift = (hash & 0x0f) << 2;  // (mod 16) * 4 ; index inside a single long

            long bucketMask = 15L << bucketShift;
            long bucketValue = (buckets[bucketIndex] & bucketMask) >>> bucketShift;

            if (bucketValue < res) res = (int)bucketValue;
        }
        if (res != Integer.MAX_VALUE) {
            return res;
        } else {
            return 0;
        }
    }

    public int[] getHashBuckets(String key, int hashCount, int max) {
        byte[] b;
        try {
            b = key.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return getHashBuckets(b, hashCount, max);
    }

    int[] getHashBuckets(byte[] b, int hashCount, int max) {
        int[] result = new int[hashCount];
        int hash1 = MurmurHash.hash(b, b.length, 0);
        int hash2 = MurmurHash.hash(b, b.length, hash1);
        for (int i = 0; i < hashCount; i++) {
            result[i] = Math.abs((hash1 + i * hash2) % max);
        }
        return result;
    }

    public static void main(String[] args) {
        CountingBloomFilter filter = new CountingBloomFilter(12, 1d);
        filter.add("test");
        System.out.println(filter.isPresent("test"));
        System.out.println(filter.count("test"));
        filter.add("test");
        filter.add("test");
        filter.add("test");
        filter.add("test");
        System.out.println(filter.count("test"));
        filter.delete("test");
        System.out.println(filter.count("test"));

        System.out.println(filter.isPresent("test1"));
    }

}
