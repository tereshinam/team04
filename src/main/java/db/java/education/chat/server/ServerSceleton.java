package db.java.education.chat.server;

import jdk.nashorn.internal.ir.RuntimeNode;

import javax.sql.rowset.spi.SyncProvider;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSceleton {
    public static List<ClientHandler> clientList = new ArrayList<>();
    public static List<String> journal = new ArrayList<>(1000);
    private static volatile boolean serverIsWork = true;

    public static void main(String[] args) throws IOException {
        Socket client = null;
        Runnable shotdownTask = new Runnable() {
            @Override
            //TODO:try in writer
            public void run() {
                JournalWriter jw=new JournalWriter("journal.txt");
                jw.writeJournal(journal);
            }
        };
        Runtime.getRuntime().addShutdownHook(new Thread(shotdownTask));
        setUpServerJournal();
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (serverIsWork) {
                client = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                new Thread(clientHandler).start();
                clientList.add(clientHandler);
            }
        } catch (IOException e) {
            if (client != null)
                clientList.remove(client);
            e.printStackTrace();
        }
    }

    private static void setUpServerJournal(){
        JournalReader journalReader = new JournalReader("journal.txt");
        journalReader.readJournal(journal);
    }

}
