package com.arturmkrtchyan.mintds.cli;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import jline.TerminalFactory;
import jline.console.ConsoleReader;

public class MintDsClient {

    private static final String HOST = System.getProperty("host", "127.0.0.1");
    private static final int PORT = Integer.parseInt(System.getProperty("port", "7657"));

    private static final MintDsClientHandler clientHandler = new MintDsClientHandler();

    public static void main(String[] args) throws Exception {

        ConsoleReader console = new ConsoleReader(null, System.in, System.out, null);
        console.setPrompt("\nmintDS> ");
        console.setBellEnabled(false);


        EventLoopGroup group = new NioEventLoopGroup(1);
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new MintDsClientInitializer(clientHandler));

            // Start the connection attempt.
            Channel ch = b.connect(HOST, PORT).sync().channel();

            // Read commands from the stdin.
            ChannelFuture lastWriteFuture = null;
            for (;;) {
                String line = console.readLine();
                if (line == null) {
                    break;
                }

                if(line.trim().isEmpty()) {
                    continue;
                }

                // If user typed the 'bye' command, wait until the server closes
                // the connection.
                if ("bye".equals(line.toLowerCase())) {
                    ch.closeFuture().sync();
                    break;
                }

                // Sends the received line to the server.
                lastWriteFuture = ch.writeAndFlush(line + "\r\n").sync();

                // Waits for the response
                System.out.println(clientHandler.queue().take());
            }

            // Wait until all messages are flushed before closing the channel.
            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }
        } finally {
            group.shutdownGracefully();
            TerminalFactory.get().restore();
        }


    }


}
