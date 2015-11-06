package com.arturmkrtchyan.mintds.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.util.function.Supplier;

public class MintDsClientInitializer extends ChannelInitializer<SocketChannel> {

    private final StringDecoder stringDecoder = new StringDecoder(CharsetUtil.UTF_8);
    private final StringEncoder stringEncoder = new StringEncoder(CharsetUtil.UTF_8);
    private final Supplier<MintDsClientHandler> handlerSupplier;

    public MintDsClientInitializer(Supplier<MintDsClientHandler> handlerSupplier) {
        this.handlerSupplier = handlerSupplier;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        // decoders
        // Add the text line codec combination first,
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("stringDecoder", stringDecoder);

        // encoders
        pipeline.addLast("stringEncoder", stringEncoder);

        // business logic handler
        pipeline.addLast("clientHandler", handlerSupplier.get());
    }
}
