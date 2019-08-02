package db.java.education.chat.client;

import db.java.education.chat.protocol.Protocol;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ClientWriter {
    Socket client;

    public ClientWriter(Socket client) {
        this.client = client;
    }

    public void comeOnWriting() throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        {

            try (BufferedReader console =
                         new BufferedReader(
                                 new InputStreamReader(
                                         new BufferedInputStream(
                                                 System.in, 184)))) {

                while (true) {
                    String putLine = console.readLine();
                    switch (putLine.contains(" ") ? putLine.substring(0, putLine.indexOf(" ")) : putLine) {
                        case Protocol.SEND_MESSAGE:
                            out.write(Protocol.SEND_MESSAGE+" " + new Date().toString()
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
                e.printStackTrace();
            }
        }
    }
}
