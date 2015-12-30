package com.arturmkrtchyan.mintds.config;

public class BloomFilterConfig {

    private float probability;

    public void setProbability(float probability) {
        this.probability = probability;
    }

    public float getProbability() {
        return probability;
    }

    @Override
    public String toString() {
        return "BloomFilterConfig{" +
                "probability=" + probability +
                '}';
    }
}
