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

        // FIXME handle exceptions and response immediately

        String[] msgParts = msg.split(" ");

        final Optional<Command> command = getCommand(msgParts[0]);
        final Optional<DataStructure> dataStructure = getDataStructure(msgParts[1]);
        final Optional<String> key = Optional.of(msgParts[2].trim());
        Optional<String> value = Optional.empty();

        if((command.get() == Command.ADD ||
                command.get() == Command.CONTAINS ||
                command.get() == Command.COUNT) && msgParts.length > 3) {
            value = Optional.of(msgParts[3].trim());
        }


        // parse the message to the right request.
        final Request request = new DefaultRequest.Builder()
                .withCommand(command.orElseThrow(IllegalStateException::new))
                .withDataStructure(dataStructure.orElseThrow(IllegalStateException::new))
                .withKey(key.orElseThrow(IllegalStateException::new))
                .withValue(value)
                .build();
        out.add(request);
    }

    protected Optional<Command> getCommand(final String command) {
        try {
            return Command.fromString(command.trim());
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    protected Optional<DataStructure> getDataStructure(final String dataStructure) {
        try {
            return DataStructure.fromString(dataStructure.trim());
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
