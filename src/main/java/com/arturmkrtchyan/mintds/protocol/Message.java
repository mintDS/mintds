package com.arturmkrtchyan.mintds.protocol;

public class Message {

    private Command command;
    private DataStructure dataStructure;
    private String key;
    private String value;

    private Message(Command command, DataStructure dataStructure, String key, String value) {
        this.command = command;
        this.dataStructure = dataStructure;
        this.key = key;
        this.value = value;
    }

    public DataStructure getDataStructure() {
        return dataStructure;
    }

    public Command getCommand() {
        return command;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "Message{" +
                "command=" + command +
                ", dataStructure=" + dataStructure +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public final static class Builder {
        private Command command;
        private DataStructure dataStructure;
        private String key;
        private String value;

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

        public Builder withValue(final String value) {
            this.value = value;
            return this;
        }

        public Message build() {
            return new Message(command, dataStructure, key, value);
        }

    }
}
