package db.java.education.tests;

import db.java.education.chat.protocol.Command;
import db.java.education.chat.protocol.CommandType;
import db.java.education.chat.protocol.Protocol;
import org.junit.Assert;
import org.junit.Test;


public class ProtocolTest {
    Command sut;

    @Test
    public void shouldSndStringParseCorrectly()
    {
        sut = Protocol.getParseCommand("/snd hello");
        Assert.assertEquals(CommandType.SEND_MESSAGE, sut.getType());
        Assert.assertEquals("hello", sut.getArgs());
    }

    @Test
    public void shouldHistStringParseCorrectly()
    {
        sut = Protocol.getParseCommand("/hist");
        Assert.assertEquals(CommandType.SHOW_HISTORY, sut.getType());
        Assert.assertEquals("showing history", sut.getArgs());
    }

    @Test
    public void shouldNoCommandStringParseCorrectly()
    {
        sut = Protocol.getParseCommand("sfagjafdjhg");
        Assert.assertEquals(CommandType.UNKNOWN_COMMAND, sut.getType());
        Assert.assertEquals("unknown command", sut.getArgs());
    }

}
