package com.arturmkrtchyan.mintds.cli;

import com.arturmkrtchyan.mintds.protocol.response.Response;
import jline.TerminalFactory;
import jline.console.ConsoleReader;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.arturmkrtchyan.mintds.client.MintDsClient;

public class MintDsTerminal {

    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 7657;

    public static void main(String[] args) throws Exception {

        final Optional<String> host = args.length > 0 ? Optional.of(args[0]) : Optional.empty();
        final Optional<Integer> port = args.length > 1 ? Optional.of(Integer.valueOf(args[1])) : Optional.empty();

        try {
            ConsoleReader console = new ConsoleReader(null, System.in, System.out, null);
            console.setPrompt("\nmintDS> ");
            console.setBellEnabled(false);

            MintDsClient client = new MintDsClient.Builder()
                    .host(host.orElse(DEFAULT_HOST))
                    .port(port.orElse(DEFAULT_PORT))
                    .numberOfThreads(1)
                    .numberOfConnections(1)
                    .build();

            for (; ;) {
                String line = console.readLine();
                if (line == null || "bye".equals(line.toLowerCase())) {
                    break;
                }

                if (line.trim().isEmpty()) {
                    continue;
                }

                // Waits for the response
                CompletableFuture<Response> future = client.send(line);
                System.out.println(future.get());
            }

            client.close();

        } finally {
            TerminalFactory.get().restore();
        }
    }
}
