package com.arturmkrtchyan.mintds.cli;

import jline.TerminalFactory;
import jline.console.ConsoleReader;

import java.util.Optional;

public class MintDsTerminal {

    public static void main(String[] args) throws Exception {

        final Optional<String> host = args.length > 0 ? Optional.of(args[0]) : Optional.empty();
        final Optional<Integer> port = args.length > 1 ? Optional.of(Integer.valueOf(args[1])) : Optional.empty();

        try {
            ConsoleReader console = new ConsoleReader(null, System.in, System.out, null);
            console.setPrompt("\nmintDS> ");
            console.setBellEnabled(false);

            MintDsClient client = new MintDsClient(host, port);
            client.connect();

            for (; ;) {
                String line = console.readLine();
                if (line == null || "bye".equals(line.toLowerCase())) {
                    break;
                }

                if (line.trim().isEmpty()) {
                    continue;
                }

                // Waits for the response
                System.out.println(client.send(line));
            }

            client.disconnect();

        } finally {
            TerminalFactory.get().restore();
        }
    }

}
