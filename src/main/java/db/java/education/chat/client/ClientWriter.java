package db.java.education.chat.client;

import db.java.education.chat.protocol.Command;
import db.java.education.chat.protocol.Protocol;
import db.java.education.chat.protocol.CommandType;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ClientWriter {
    Socket client;
    private CommandType type;
    private String args;

    public ClientWriter(Socket client) throws IOException {
        this.client = client;
    }

    public void comeOnWriting() throws IOException {
        final ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
        {

            try (BufferedReader console =
                         new BufferedReader(
                                 new InputStreamReader(
                                         new BufferedInputStream(
                                                 System.in, 184)))) {

                while (true) {
                    String putLine = console.readLine();
                    switch (putLine.contains(" ") ? putLine.substring(0, putLine.indexOf(" ")) : putLine) {
                        case "/snd":
                            out.writeObject(Protocol.getParseCommand(
                                    "/snd " + new Date().toString()
                                            + putLine.replaceFirst("/snd", " ")));
                            break;
                        default:
                            out.writeObject(Protocol.getParseCommand(putLine));
                            break;
                    }

                }

                //endregion
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
