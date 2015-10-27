package com.arturmkrtchyan.mintds.server.netty;

import com.arturmkrtchyan.mintds.server.MintDsServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.Optional;

public class NettyServer implements MintDsServer {

    private Optional<EventLoopGroup> bossGroup;
    private Optional<EventLoopGroup> workerGroup;

    public void start() {
        bossGroup = Optional.of(new NioEventLoopGroup(1));
        workerGroup = Optional.of(new NioEventLoopGroup());
        try {
            final ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup.get(), workerGroup.get())
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new NettyServerInitializer());

            b.bind(PORT).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            stop();
        }
    }

    public void stop() {
        bossGroup.ifPresent(EventExecutorGroup::shutdownGracefully);
        workerGroup.ifPresent(EventExecutorGroup::shutdownGracefully);
    }

}
