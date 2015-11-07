package com.arturmkrtchyan.mintds.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

public class MintDsClient {

    //https://github.com/jonathanedgecombe/opencube/blob/6570e5dea1c54ca28c85e4d8595d2b656d27161e/src/main/java/com/opencube/server/io/pipeline/MinecraftHandler.java
    // https://github.com/jonathanedgecombe/opencube/blob/6570e5dea1c54ca28c85e4d8595d2b656d27161e/src/main/java/com/opencube/server/io/Attributes.java

    // https://github.com/wg/lettuce
    // https://github.com/AsyncHttpClient/async-http-client

    private static final int DEFAULT_THREADS = 1;
    private static final int DEFAULT_CONNECTIONS = 16;

    private String host;
    private int port;
    private int numberOfThreads;
    private int numberOfConnections;
    private List<Channel> channels;
    private Random random;

    private EventLoopGroup eventLoopGroup;

    private MintDsClient() {}

    private void connect() throws Exception {
        MintDsClientHandler handler = new MintDsClientHandler();
        eventLoopGroup = new NioEventLoopGroup(numberOfThreads);
        Bootstrap b = new Bootstrap();
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new MintDsClientInitializer(MintDsClientHandler::new));

        // Start the connection attempt.
        IntStream.range(0, numberOfConnections).forEach(value -> {
            try {
                Channel channel = b.connect(host, port).sync().channel();
                channels.add(channel);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        ;
    }

    public void disconnect() throws Exception {
        channels.stream().forEach(channel -> {
            try {
                channel.closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        eventLoopGroup.shutdownGracefully();
    }

    public void send(final String message) throws Exception {
        // Sends the message to the server.
        Channel channel = randomChannel();
        channel.writeAndFlush(message + "\r\n").sync();
    }

    protected Channel randomChannel() {
        return channels.get(random.nextInt(numberOfConnections));
    }

    public static void main(String[] args) throws Exception {

        MintDsClient client = new MintDsClient.Builder()
                .host("localhost")
                .port(7657)
                .numberOfConnections(16)
                .numberOfThreads(1)
                .build();

        long start = System.currentTimeMillis();

        client.send("create bloomfilter test");

        IntStream.range(0, 100).forEach(value -> {
            try {
                client.send("add bloomfilter test mytestvalue"+value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        long end = System.currentTimeMillis();

        System.out.println(end - start);
        //client.disconnect();

    }

    public static class Builder {

        private MintDsClient client = new MintDsClient();

        public Builder host(final String host) {
            client.host = host;
            return this;
        }

        public Builder port(final int port) {
            client.port = port;
            return this;
        }

        public Builder numberOfThreads(final int threads) {
            client.numberOfThreads = threads;
            return this;
        }

        public Builder numberOfConnections(final int connections) {
            client.numberOfConnections = connections;
            return this;
        }

        public MintDsClient build() {
            client.channels = new ArrayList<>(client.numberOfConnections);
            client.random = new Random();

            try {
                client.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return client;
        }

    }

}
