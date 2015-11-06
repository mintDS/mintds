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

    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 7657;
    private static final int DEFAULT_THREADS = 1;
    private static final int DEFAULT_CONNECTIONS = 16;

    private final String host;
    private final int port;
    private final int numberOfThreads;
    private final int numberOfConnections;
    private final List<Channel> channels;
    private final Random random;

    private EventLoopGroup eventLoopGroup;

    public MintDsClient(final Optional<String> host,
                        final Optional<Integer> port) {
        this(host, port, Optional.empty(), Optional.empty());
    }

    public MintDsClient(final Optional<String> host,
                        final Optional<Integer> port,
                        final Optional<Integer> numberOfThreads,
                        final Optional<Integer> numberOfConnections) {
        this.host = host.orElse(DEFAULT_HOST);
        this.port = port.orElse(DEFAULT_PORT);
        this.numberOfThreads = numberOfThreads.orElse(DEFAULT_THREADS);
        this.numberOfConnections = numberOfConnections.orElse(DEFAULT_CONNECTIONS);
        this.channels = new ArrayList<>(this.numberOfConnections);
        this.random = new Random();

        try {
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

        final Optional<String> host = args.length > 0 ? Optional.of(args[0]) : Optional.empty();
        final Optional<Integer> port = args.length > 1 ? Optional.of(Integer.valueOf(args[1])) : Optional.empty();

        MintDsClient client = new MintDsClient(host, port);

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

}
