package com.arturmkrtchyan.mintds.cli;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MintDsClient {

    private static final String HOST = System.getProperty("host", "127.0.0.1");
    private static final int PORT = Integer.parseInt(System.getProperty("port", "7657"));

    private final MintDsClientHandler clientHandler;
    private EventLoopGroup eventLoopGroup;
    private Channel channel;

    public MintDsClient() {
        clientHandler = new MintDsClientHandler();
    }

    public void connect() throws Exception {
        eventLoopGroup = new NioEventLoopGroup(1);
        Bootstrap b = new Bootstrap();
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new MintDsClientInitializer(clientHandler));

        // Start the connection attempt.
        channel = b.connect(HOST, PORT).sync().channel();
    }

    public void disconnect() throws Exception {
        channel.close().sync();
        eventLoopGroup.shutdownGracefully();
    }

    public String send(final String message) throws Exception {
        // Sends the message to the server.
        channel.writeAndFlush(message + "\r\n").sync();

        // Waits for the response
        return clientHandler.queue().take();
    }

}
