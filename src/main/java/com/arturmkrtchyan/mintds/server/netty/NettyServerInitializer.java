package com.arturmkrtchyan.mintds.server.netty;

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

    private final StringDecoder stringDecoder = new StringDecoder(CharsetUtil.UTF_8);
    private final StringEncoder stringEncoder = new StringEncoder(CharsetUtil.UTF_8);
    private final RequestDecoder requestDecoder = new RequestDecoder();
    private final ResponseEncoder responseEncoder = new ResponseEncoder();
    private final NettyServerHandler serverHandler = new NettyServerHandler();

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
