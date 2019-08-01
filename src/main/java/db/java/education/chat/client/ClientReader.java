package db.java.education.chat.client;

import java.io.*;
import java.net.Socket;

public class ClientReader {
    Socket server;

    public ClientReader(Socket server) {
        this.server = server;
    }

    public void comeOn() {
        Thread thread = new Thread(() -> {
            try (final BufferedReader in =
                         new BufferedReader(
                                 new InputStreamReader(
                                         new BufferedInputStream(
                                                 server.getInputStream(), 100)))) {

                while (!Thread.interrupted()) {
                    System.out.println(in.readLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        thread.setDaemon(true);
        thread.start();
    }
}
