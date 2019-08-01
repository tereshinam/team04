package db.java.education.chat.server;

import javax.sql.rowset.spi.SyncProvider;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSceleton {
    public static List<ClientHandler> clientList = new ArrayList<>();
    public static List<String> journal = new ArrayList<>();

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(666)){
            while(true){
                Socket client = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                clientList.add(clientHandler);
                new Thread(clientHandler).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String journalToString(){
        StringBuilder history = new StringBuilder();
        for(String s:journal){
            history.append(s);
            history.append(System.lineSeparator());
        }
        return history.toString();
    }
}
