package db.java.education.chat.client;

import db.java.education.chat.protocol.Protocol;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

class ClientWriter {
    private Socket client;

    ClientWriter(Socket client) {
        this.client = client;
    }

    /**
     * Method creates out stream that sends strings to server
     * and console stream that reads strings from console
     */
    void comeOnWriting() {
        Logger logger = Logger.getLogger("Client");
        try (BufferedWriter out = new BufferedWriter
                (new OutputStreamWriter(client.getOutputStream()));
             BufferedReader console =
                     new BufferedReader(
                             new InputStreamReader(
                                     new BufferedInputStream(
                                             System.in, 300)))) {

            while (true) {
                String putLine = console.readLine();
                switch (putLine.contains(" ") ? putLine.substring(0, putLine.indexOf(" ")) : putLine) {
                    case Protocol.SEND_MESSAGE:
                        out.write(Protocol.SEND_MESSAGE + " " + new Date().toString()
                                + putLine.replaceFirst(Protocol.SEND_MESSAGE, " "));
                        out.newLine();
                        out.flush();
                        break;
                    default:
                        out.write(putLine);
                        out.newLine();
                        out.flush();
                        break;
                }

            }

        } catch (IOException e) {
            logger.log(Level.INFO, "Client failed to send command");
            System.out.println("Failed to execute command!");
        }
    }
}

