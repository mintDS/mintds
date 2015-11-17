package com.arturmkrtchyan.mintds.cli;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Optional;

public class MintDsClient {

    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 7657;

    private final String host;
    private final int port;

    private final MintDsClientHandler clientHandler;
    private EventLoopGroup eventLoopGroup;
    private Channel channel;

    public MintDsClient(final Optional<String> host, final Optional<Integer> port) {
        this.host = host.orElse(DEFAULT_HOST);
        this.port = port.orElse(DEFAULT_PORT);
        clientHandler = new MintDsClientHandler();
    }

    public void connect() throws Exception {
        eventLoopGroup = new NioEventLoopGroup(1);
        Bootstrap b = new Bootstrap();
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new MintDsClientInitializer(clientHandler));

        // Start the connection attempt.
        channel = b.connect(host, port).sync().channel();
    }

    public void disconnect() throws Exception {
        channel.close().sync();
        eventLoopGroup.shutdownGracefully();
    }

    public String send(final String message) throws RuntimeException {
        try {
            // Sends the message to the server.
            channel.writeAndFlush(message + "\r\n").sync();
            // Waits for the response
            return clientHandler.queue().take().toString().toLowerCase();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
