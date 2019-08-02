package db.java.education.chat.server;

import db.java.education.chat.protocol.Command;
import db.java.education.chat.protocol.Protocol;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {
    private Socket client;
    private BufferedReader in;
    private BufferedWriter out;
    private Logger logger = Logger.getLogger("Client thread");

    public ClientHandler(Socket client) throws IOException {
        logger.log(Level.INFO,"new client");
        this.client = client;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = in.readLine();
                logger.log(Level.INFO,"accept message :"+message);
                Command currentCommand = Protocol.getParseCommand(message);
                handleCommand(currentCommand);
            }
        } catch (IOException ex) {
            logger.log(Level.WARNING,"client out");
            ServerSceleton.clientList.remove(this);
        } finally {
            try {
                out.close();
            }catch (IOException e){
                logger.log(Level.WARNING,"can't close data output stream");
            }
            try {
                in.close();
            }catch (IOException e){
                logger.log(Level.WARNING,"can't close data input stream");
            }
        }
    }

    private void handleCommand(Command command) {
        switch (command.getType()) {
            case SEND_MESSAGE:
                sendMessageAllClient(command.getArgs());
                ServerSceleton.journal.add(command.getArgs());
                break;
            case SHOW_HISTORY:
                sendMessage(Utils.journalToString(ServerSceleton.journal));
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
