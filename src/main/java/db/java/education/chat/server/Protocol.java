package db.java.education.chat.server;

public class Protocol {
    private static String delimetr = " ";

    public static Command getParseCommand(String message){
        //TODO:limit of split
        String[] strMas = message.split(delimetr,2);

        CommandType type;
        String args = "";
        switch (strMas[0]) {
            case "/snd":
                type = CommandType.SEND_MESSAGE;
                args = strMas[1];
                break;
            default:
                type = CommandType.UNKNOWN_COMMAND;
                args = "unknown command";
        }
        return new Command(type,args);
    }
}
