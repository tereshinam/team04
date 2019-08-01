package db.java.education.chat.server;

import db.java.education.chat.protocol.Command;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket client;
    private ObjectInputStream in;
    private BufferedWriter out;

    public ClientHandler(Socket client) throws IOException {
        this.client = client;
        in = new ObjectInputStream(client.getInputStream());
        out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                Command message = (Command)in.readObject();
                System.out.println("accept message :"+message.getArgs());
                handleCommand(message);
            }
        } catch (IOException ex) {
            try {
                out.close();
                in.close();
            }catch (IOException e){
                e.printStackTrace();;
            }
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handleCommand(Command command) {
        switch (command.getType()) {
            case SEND_MESSAGE:
                sendMessageAllClient(command.getArgs());
                ServerSceleton.journal.add(command.getArgs());
                break;
            case SHOW_HISTORY:
                sendMessage(ServerSceleton.journalToString());
            case UNKNOWN_COMMAND:
                sendMessage(command.getArgs());
                break;
        }
    }

    public void sendMessageAllClient(String message) {
        for (ClientHandler client : ServerSceleton.clientList) {
            client.sendMessage(message);
        }
    }

    private void sendMessage(String message) {
        try {
            out.write(message);
            out.newLine();
            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
