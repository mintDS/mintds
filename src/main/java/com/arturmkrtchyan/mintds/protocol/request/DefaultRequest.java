package com.arturmkrtchyan.mintds.protocol.request;

import java.util.Optional;

public class DefaultRequest implements Request {

    private Command command;
    private DataStructure dataStructure;
    private String key;
    private Optional<String> value;

    private DefaultRequest(Command command, DataStructure dataStructure, String key, Optional<String> value) {
        this.command = command;
        this.dataStructure = dataStructure;
        this.key = key;
        this.value = value;
    }

    public static Request fromString(final String msg) {
        String[] msgParts = msg.split(" ");

        final Command command = Command.fromString(msgParts[0].trim())
                .orElseThrow(() -> new IllegalStateException("Invalid Command."));

        if(msgParts.length < 2) {
            throw new IllegalStateException("DataStructure is missing.");
        }
        final DataStructure dataStructure = DataStructure.fromString(msgParts[1].trim())
                .orElseThrow(() -> new IllegalStateException("Invalid DataStructure."));

        if(msgParts.length < 3) {
            throw new IllegalStateException("Key is missing.");
        }
        final String key = msgParts[2].trim();

        Optional<String> value = Optional.empty();

        if(dataStructure == DataStructure.BloomFilter &&
                (command == Command.ADD || command == Command.CONTAINS)) {
            value = getValue(msgParts);
        }

        if(dataStructure == DataStructure.HyperLogLog && command == Command.ADD) {
            value = getValue(msgParts);
        }

        if(dataStructure == DataStructure.CountMinSketch &&
                (command == Command.ADD || command == Command.COUNT)) {
            value = getValue(msgParts);
        }


        // parse the message to the right request.
        return new DefaultRequest.Builder()
                .withCommand(command)
                .withDataStructure(dataStructure)
                .withKey(key)
                .withValue(value)
                .build();
    }

    private static Optional<String> getValue(final String[] msg) {
        if(msg.length < 4) {
            throw new IllegalStateException("Value is missing.");
        }
        return Optional.of(msg[3].trim());
    }

    public final static class Builder {
        private Command command;
        private DataStructure dataStructure;
        private String key;
        private Optional<String> value;

        public Builder() {
        }

        public Builder withCommand(final Command command) {
            this.command = command;
            return this;
        }

        public Builder withDataStructure(final DataStructure dataStructure) {
            this.dataStructure = dataStructure;
            return this;
        }

        public Builder withKey(final String key) {
            this.key = key;
            return this;
        }

        public Builder withValue(final Optional<String> value) {
            this.value = value;
            return this;
        }

        public DefaultRequest build() {
            return new DefaultRequest(command, dataStructure, key, value);
        }

    }

    @Override
    public DataStructure getDataStructure() {
        return dataStructure;
    }

    @Override
    public Command getCommand() {
        return command;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Optional<String> getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "DefaultRequest{" +
                "command=" + command +
                ", dataStructure=" + dataStructure +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
