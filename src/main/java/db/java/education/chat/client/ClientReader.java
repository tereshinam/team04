package db.java.education.chat.client;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientReader {
    Socket server;
    Logger logger;

    public ClientReader(Socket server) {
        this.server = server;
    }

    /**
     * Method creates a thread that endlessly read server messages
     */
    public void comeOn() {
        final Executor pool = Executors.newSingleThreadExecutor();
        pool.execute(() -> {
            try (final BufferedReader in =
                         new BufferedReader(
                                 new InputStreamReader(
                                         new BufferedInputStream(
                                                 server.getInputStream(), 340)))) {

                while (!Thread.interrupted()) {
                    System.out.println(in.readLine());
                }
            } catch (IOException e) {
                logger.log(Level.INFO, "Client's reading thread was interrupted");
                System.out.println("Error! Cannot read messages from server! Please restart system.");
            }

        });
    }
}
