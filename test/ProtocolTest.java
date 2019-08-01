import db.java.education.chat.protocol.Command;
import db.java.education.chat.protocol.CommandType;
import db.java.education.chat.protocol.Protocol;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class ProtocolTest {
    @Test
    public void shouldStringParseCorrectly()
    {
        Command sut1 = Protocol.getParseCommand("/snd vodka");
        Command sut2 = Protocol.getParseCommand("/hist");
        Command sut3 = Protocol.getParseCommand("sfagjafdjhg");
        Assert.assertEquals(CommandType.SEND_MESSAGE, sut1.getType());
        Assert.assertEquals("vodka", sut1.getArgs());
        Assert.assertEquals(CommandType.SHOW_HISTORY, sut2.getType());
        Assert.assertEquals(" ", sut2.getArgs());
        Assert.assertEquals(CommandType.UNKNOWN_COMMAND, sut3.getType());
        Assert.assertEquals("unknown command", sut3.getArgs());
    }
}
