package com.arturmkrtchyan.mintds.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class BloomFilterConfig {

    private int elements;
    private double probability;

}
