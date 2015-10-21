package com.arturmkrtchyan.mintds.server;

import com.arturmkrtchyan.mintds.protocol.Command;
import com.arturmkrtchyan.mintds.protocol.DataStructure;
import com.arturmkrtchyan.mintds.protocol.Message;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

@Sharable
public class MessageDecoder extends MessageToMessageDecoder<String> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, String msg, List<Object> out) throws Exception {
        Message message = new Message.Builder()
                .withCommand(Command.CREATE)
                .withDataStructure(DataStructure.BloomFilter)
                .withKey(String.valueOf(Math.random()))
                .build();
        out.add(message);
    }

}
