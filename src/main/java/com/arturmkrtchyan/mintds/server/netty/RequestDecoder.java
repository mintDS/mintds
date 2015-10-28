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

@Sharable
public class RequestDecoder extends MessageToMessageDecoder<String> {

    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final String msg, final List<Object> out) {

        String[] msgParts = msg.split(" ");

        final Optional<Command> command = getCommand(msgParts[0]);
        if(command.get() == Command.CREATE) {
            final Optional<DataStructure> dataStructure = getDataStructure(msgParts[1]);
        }

        // parse the message to the right request.
        final Request request = new DefaultRequest.Builder()
                .withCommand(Command.CREATE)
                .withDataStructure(DataStructure.BloomFilter)
                .withKey(String.valueOf(Math.random()))
                .build();
        out.add(request);
    }

    protected Optional<Command> getCommand(final String command) {
        try {
            return Optional.of(Command.valueOf(command.trim().toUpperCase()));
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    protected Optional<DataStructure> getDataStructure(final String dataStructure) {
        try {
            return Optional.of(DataStructure.valueOf(dataStructure.trim().toUpperCase()));
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
