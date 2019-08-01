package db.java.education.chat.server;

import java.io.*;
import java.net.Socket;

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
        try {
            while (true) {
                String message = in.readLine();
                Command currentCommand = Protocol.getParseCommand(message);
                handleCommand(currentCommand);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
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
