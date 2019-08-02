package db.java.education.chat.server;

import jdk.nashorn.internal.ir.RuntimeNode;
import sun.rmi.runtime.Log;

import javax.sql.rowset.spi.SyncProvider;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSceleton {
    public static ReentrantReadWriteLock clientCollectionLock = new ReentrantReadWriteLock();
    public static ReentrantReadWriteLock journalLock = new ReentrantReadWriteLock();
    public static List<ClientHandler> clientList = new ArrayList<>();
    public static List<String> journal = new ArrayList<>(1000);
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
                logger.log(Level.INFO, "accepting client");
                client = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                new Thread(clientHandler).start();
                clientCollectionLock.writeLock().lock();
                clientList.add(clientHandler);
                clientCollectionLock.writeLock().unlock();
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "exception throed");
            if (client != null) {
                clientCollectionLock.writeLock().lock();
                clientList.remove(client);
                clientCollectionLock.writeLock().unlock();
            }
            e.printStackTrace();
        }
    }

    private static void setUpServerJournal() {
        logger.log(Level.INFO, "setup history of messages");
        JournalReader journalReader = new JournalReader("journal.txt");
        journalReader.readJournal(journal);
    }

}
