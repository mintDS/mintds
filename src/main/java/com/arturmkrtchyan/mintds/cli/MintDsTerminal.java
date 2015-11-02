package com.arturmkrtchyan.mintds.cli;

import jline.TerminalFactory;
import jline.console.ConsoleReader;

public class MintDsTerminal {

    public static void main(String[] args) throws Exception {
        try {
            ConsoleReader console = new ConsoleReader(null, System.in, System.out, null);
            console.setPrompt("\nmintDS> ");
            console.setBellEnabled(false);

            MintDsClient client = new MintDsClient();
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
