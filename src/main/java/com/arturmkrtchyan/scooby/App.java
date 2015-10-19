package com.arturmkrtchyan.scooby;

import com.arturmkrtchyan.scooby.server.ScoobyServer;

public class App 
{
    public static void main( String[] args ) throws InterruptedException {
        System.out.println( "Hello World!" );
        ScoobyServer server = new ScoobyServer();
        server.run();
    }
}
