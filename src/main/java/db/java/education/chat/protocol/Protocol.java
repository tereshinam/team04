package db.java.education.chat.protocol;

public class Protocol {
    public final static String DELIMETR = " ";
    public final static String SEND_MESSAGE = "/snd";
    public final static String SHOW_HISTORY = "/hist";
    public final static String UNKNOWN_COMMAND = "unknown command";

    /**
     * Parses string into command according to first substring (command)
     *
     * @param message
     * @return parsed Command
     */
    public static Command getParseCommand(String message) {
        String[] strMas = message.split(DELIMETR, 2);

        CommandType type;
        String args;
        switch (strMas[0]) {
            case SEND_MESSAGE:
                type = CommandType.SEND_MESSAGE;
                args = strMas[1];
                break;
            case SHOW_HISTORY:
                type = CommandType.SHOW_HISTORY;
                args = "showing history";
                break;
            default:
                type = CommandType.UNKNOWN_COMMAND;
                args = UNKNOWN_COMMAND;
        }
        return new Command(type, args);
    }
}
