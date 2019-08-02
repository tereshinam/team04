package db.java.education.chat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSceleton {
    static List<ClientHandler> clientList = new ArrayList<>();
    static List<String> journal = new ArrayList<>(1000);
    private static Logger logger = Logger.getLogger("Server");

    public static void main(String[] args) throws IOException {
        Socket client = null;
        Runnable shotdownTask = new Runnable() {
            @Override
            //TODO:try in writer
            public void run() {
                logger.log(Level.INFO, "starting shutdown");
                JournalWriter jw = new JournalWriter("journal.txt");
                jw.writeJournal(journal);
                logger.log(Level.INFO, "shutdown is fine");
            }
        };
        Runtime.getRuntime().addShutdownHook(new Thread(shotdownTask));
        setUpServerJournal();
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                logger.log(Level.INFO, "accepting client number " + clientList.size());
                client = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                new Thread(clientHandler).start();
                clientList.add(clientHandler);
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "exception throed");
            if (client != null)
                clientList.remove(client);
            e.printStackTrace();
        }
    }

    private static void setUpServerJournal() {
        logger.log(Level.INFO, "setup history of messages");
        JournalReader journalReader = new JournalReader("journal.txt");
        journalReader.readJournal(journal);
    }

}
