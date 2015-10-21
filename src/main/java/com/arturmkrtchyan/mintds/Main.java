package com.arturmkrtchyan.mintds;

import com.arturmkrtchyan.mintds.server.Server;
import net.openhft.hashing.LongHashFunction;

public class Main
{
    public static void main( String[] args ) throws InterruptedException {

        //long hash = LongHashFunction.murmur_3().hashChars("Hello");
        //System.out.println(hash%16000000);

        System.out.println( "Hello World!" );
        Server server = new Server();
        server.run();
    }
}
