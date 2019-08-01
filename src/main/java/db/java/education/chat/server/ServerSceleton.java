package db.java.education.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSceleton {
    private static List<ClientHandler> clientList = new ArrayList<>();

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(8080)){
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
}
