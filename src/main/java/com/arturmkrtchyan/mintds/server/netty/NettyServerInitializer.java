package com.arturmkrtchyan.mintds.server.netty;

import com.arturmkrtchyan.mintds.core.KeyValueStoreRouter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    // Use internal executor if this doesn't scale as netty pins connections to threads.
    private final EventExecutorGroup group = new DefaultEventExecutorGroup(16);

    private final StringDecoder stringDecoder;
    private final StringEncoder stringEncoder;
    private final RequestDecoder requestDecoder;
    private final ResponseEncoder responseEncoder;
    private final NettyServerHandler serverHandler;

    public NettyServerInitializer(KeyValueStoreRouter storeRouter) {
        stringDecoder = new StringDecoder(CharsetUtil.UTF_8);
        stringEncoder = new StringEncoder(CharsetUtil.UTF_8);
        requestDecoder = new RequestDecoder();
        responseEncoder = new ResponseEncoder();
        serverHandler = new NettyServerHandler(storeRouter);
    }

    @Override
    protected void initChannel(final SocketChannel ch) throws Exception {

        final ChannelPipeline pipeline = ch.pipeline();

        // decoders
        // Add the text line codec combination first,
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("stringDecoder", stringDecoder);
        pipeline.addLast("requestDecoder", requestDecoder);

        // encoders
        pipeline.addLast("stringEncoder", stringEncoder);
        pipeline.addLast("responseEncoder", responseEncoder);


        // business logic handler
        pipeline.addLast(group, "serverHandler", serverHandler);

    }
}
