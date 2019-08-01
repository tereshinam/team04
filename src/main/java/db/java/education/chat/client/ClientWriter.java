package db.java.education.chat.client;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ClientWriter {
    Socket client;

    public ClientWriter(Socket client) throws IOException {
        this.client = client;
    }

    public void comeOnWriting() throws IOException {
        try (final BufferedWriter out =
                     new BufferedWriter(
                             new OutputStreamWriter(
                                     new BufferedOutputStream(
                                             client.getOutputStream())))) {

            try (BufferedReader console =
                         new BufferedReader(
                                 new InputStreamReader(
                                         new BufferedInputStream(
                                                 System.in, 184)))) {

                while (true) {
                    String putLine = console.readLine();

                    switch (putLine.contains(" ") ? putLine.substring(0, putLine.indexOf(" ")) : putLine) {
                        case "/snd":
                            out.write("/snd " + new Date().toString() + putLine.replace("/snd", " "));
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


                //endregion
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
