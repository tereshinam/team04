package db.java.education.chat.protocol;

import java.io.Serializable;

public class Command implements Serializable {
    private CommandType type;
    private String args;

    public Command(CommandType type, String args) {
        this.type = type;
        this.args = args;
    }

    public CommandType getType() {
        return type;
    }

    public String getArgs() {
        return args;
    }
}
