package com.arturmkrtchyan.mintds.server.netty;

import com.arturmkrtchyan.mintds.protocol.response.Response;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

@Sharable
class ResponseEncoder extends MessageToMessageEncoder<Response> {

    @Override
    protected void encode(final ChannelHandlerContext ctx, final Response response, final List<Object> out) {
        out.add(response.toString().toLowerCase() + System.lineSeparator());
    }

}
