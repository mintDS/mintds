package com.arturmkrtchyan.mintds.protocol.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultRequest implements Request {

    private Command command;
    private DataStructure dataStructure;
    private String key;
    private Optional<String> value;
    private Map<String, String> options;

    private DefaultRequest(Command command,
                           DataStructure dataStructure,
                           String key,
                           Optional<String> value,
                           Map<String, String> options) {
        this.command = command;
        this.dataStructure = dataStructure;
        this.key = key;
        this.value = value;
        this.options = options;
    }

    public static Request fromString(final String msg) {
        final String[] msgParts = msg.split(" ");

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

        final String value = getValue(command, dataStructure, msgParts);

        // parse the message to the right request.
        return new DefaultRequest.Builder()
                .withCommand(command)
                .withDataStructure(dataStructure)
                .withKey(key)
                .withValue(value)
                .build();
    }

    private static String getValue(final Command command, final DataStructure dataStructure, final String[] msg) {
        if(dataStructure == DataStructure.BloomFilter &&
                (command == Command.ADD || command == Command.CONTAINS)) {
            return getValue(msg);
        }

        if(dataStructure == DataStructure.HyperLogLog && command == Command.ADD) {
            return getValue(msg);
        }

        if(dataStructure == DataStructure.CountMinSketch &&
                (command == Command.ADD || command == Command.COUNT)) {
            return getValue(msg);
        }
        return null;
    }

    private static String getValue(final String[] msg) {
        if(msg.length < 4) {
            throw new IllegalStateException("Value is missing.");
        }
        return msg[3].trim();
    }

    public final static class Builder {
        private Command command;
        private DataStructure dataStructure;
        private String key;
        private Optional<String> value;
        private Map<String, String> options;

        public Builder() {
            options = new HashMap<>();
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

        public Builder withValue(final String value) {
            this.value = Optional.ofNullable(value);
            return this;
        }

        public Builder withOption(final String key, final String value) {
            options.put(key, value);
            return this;
        }

        public DefaultRequest build() {
            return new DefaultRequest(command, dataStructure, key, value, options);
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
    public Map<String, String> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return "DefaultRequest{" +
                "command=" + command +
                ", dataStructure=" + dataStructure +
                ", key='" + key + '\'' +
                ", value=" + value +
                ", options=" + options +
                '}';
    }
}
