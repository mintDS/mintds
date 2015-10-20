package com.arturmkrtchyan.mintds;

import com.arturmkrtchyan.mintds.server.Server;

public class App 
{
    public static void main( String[] args ) throws InterruptedException {
        System.out.println( "Hello World!" );
        Server server = new Server();
        server.run();
    }
}
