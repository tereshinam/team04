package db.java.education.chat.client;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSender {
    private static Logger logger = Logger.getLogger("Client");
    private static int port = 8080;

    /**
     * Creates writer in existing thread and reader that will use separated thread
     *
     * @param args
     */
    public static void main(String[] args) {
        try (final Socket server = new Socket("localhost", port)) {
            ClientReader reader = new ClientReader(server);
            reader.comeOn();

            ClientWriter writer = new ClientWriter(server);
            writer.comeOnWriting();

        } catch (IOException e) {
            logger.log(Level.INFO, "Client failed to connect to server on port" + port);
            System.out.println("Failed to connect to server!");
        }
    }
}
