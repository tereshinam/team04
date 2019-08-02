import db.java.education.chat.protocol.Protocol;
import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class LoadTest {
//    @Test
//    public void shouldSystemNotDieInHighLoad() throws IOException {
//        try (final Socket server = new Socket("localhost", 666)) {
//            final ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
//            {
//                while (true) {
//                    String putLine = "/snd iam";
//                    switch (putLine.contains(" ") ? putLine.substring(0, putLine.indexOf(" ")) : putLine) {
//                        case "/snd":
//                            out.writeObject(Protocol.getParseCommand(
//                                    "/snd " + new Date().toString()
//                                            + putLine.replace("/snd", " ")));
//                            break;
//                        default:
//                            out.writeObject(Protocol.getParseCommand(putLine));
//                            break;
//                    }
//
//                }
//
//            }
//        }
//    }
//
}
