package db.java.education.tests;

import db.java.education.chat.client.ClientSender;
import db.java.education.chat.protocol.Protocol;
import db.java.education.chat.server.ServerSceleton;
import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class LoadTest {
   @Test
    public void shouldSystemNotToDieInHighLoad() throws IOException {
       for(int numberOfClients = 1; numberOfClients <= 100; numberOfClients++) {
           try (final Socket server = new Socket("localhost", 8080)) {
               BufferedWriter out = new BufferedWriter
                       (new OutputStreamWriter(server.getOutputStream()));
               {
                       String putLine = "/snd iam";
                       switch (putLine.contains(" ") ? putLine.substring(0, putLine.indexOf(" ")) : putLine) {
                           case "/snd":
                               out.write(Protocol.SEND_MESSAGE + " " + new Date().toString()
                                       + putLine.replaceFirst(Protocol.SEND_MESSAGE, " "));
                               out.newLine();
                               out.flush();
                               break;
                           default:
                               break;
                       }
               }
           }
       }
   }
}
