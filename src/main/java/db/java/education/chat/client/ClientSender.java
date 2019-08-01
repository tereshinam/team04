package db.java.education.chat.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class ClientSender {
    public static void main(String[] args) {
        try (final Socket server = new Socket("localhost", 666)) {
            ClientReader reader = new ClientReader(server);
            reader.comeOn();

            ClientWriter writer = new ClientWriter(server);
            writer.comeOnWriting();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
