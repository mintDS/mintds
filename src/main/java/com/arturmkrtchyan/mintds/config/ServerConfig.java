package com.arturmkrtchyan.mintds.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ServerConfig {

    private String bindAddress = "0.0.0.0";
    private int port = 7657;

}
