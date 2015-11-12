package com.arturmkrtchyan.mintds.server.netty;

import com.arturmkrtchyan.mintds.protocol.request.Command;
import com.arturmkrtchyan.mintds.protocol.request.DataStructure;
import com.arturmkrtchyan.mintds.protocol.request.DefaultRequest;
import com.arturmkrtchyan.mintds.protocol.request.Request;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Sharable
public class RequestDecoder extends MessageToMessageDecoder<String> {

    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final String msg, final List<Object> out) {
        // parse the message to the right request.
        final Request request = DefaultRequest.fromString(msg);
        out.add(request);
    }

}
