package db.java.education.chat.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class ClientSender {
    public static void main(String[] args) {
        try (final Socket server = new Socket("localhost", 8080)) {

            //region read
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
            //endregion

            //region write
            try (final BufferedWriter out =
                         new BufferedWriter(
                                 new OutputStreamWriter(
                                         new BufferedOutputStream(
                                                 server.getOutputStream())))) {

                try (BufferedReader console =
                             new BufferedReader(
                                     new InputStreamReader(
                                             new BufferedInputStream(
                                                     System.in, 184)))) {

                    while (true) {
                        String putLine = console.readLine();

                        switch (putLine.contains(" ")? putLine.substring(0,  putLine.indexOf(" ")): putLine) {
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

                }
                //endregion

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
