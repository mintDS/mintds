package com.arturmkrtchyan.mintds.server.netty;

import com.arturmkrtchyan.mintds.config.Configuration;
import com.arturmkrtchyan.mintds.config.ServerConfig;
import com.arturmkrtchyan.mintds.core.KeyValueStoreRouter;
import com.arturmkrtchyan.mintds.server.MintDsServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.Optional;

public class NettyServer {

    private Optional<EventLoopGroup> bossGroup;
    private Optional<EventLoopGroup> workerGroup;

    public void start(final Configuration configuration) {
        bossGroup = Optional.of(new NioEventLoopGroup(1));
        workerGroup = Optional.of(new NioEventLoopGroup());
        try {
            final ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup.get(), workerGroup.get())
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new NettyServerInitializer(new KeyValueStoreRouter(configuration)));

            final ServerConfig serverConfig = configuration.getServerConfig();
            b.bind(serverConfig.getBindAddress(), serverConfig.getPort())
                    .sync().channel().closeFuture().sync();
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
