package db.java.education.chat.server;

import java.io.*;
import java.net.Socket;

import static db.java.education.chat.server.CommandType.SEND_MESSAGE;

public class ClientHandler implements Runnable {
    private Socket client;
    private BufferedReader in;
    private BufferedWriter out;

    public ClientHandler(Socket client) throws IOException {
        this.client = client;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
    }

    @Override
    public void run() {
        try{
            while(true){
                String message = in.readLine();
                Command currentCommand = Protocol.getParseCommand(message);
                handleCommand(currentCommand);
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void handleCommand(Command command){
        switch (command.getType()){
            case SEND_MESSAGE:
                System.out.println(command.getArgs());
        }
    }
}
