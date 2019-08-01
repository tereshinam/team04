package db.java.education.chat.server;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static db.java.education.chat.server.Utils.journalToString;

public class JournalReader {
    private String fileName;

    public JournalReader(String fileName) {
        this.fileName = fileName;
    }

    public void readJournal(List<String> journal){
        try (
                BufferedReader fileIn = new BufferedReader(
                        new InputStreamReader(
                                new BufferedInputStream(
                                        new FileInputStream(
                                                new File(fileName)))))) {
            journal.addAll(fileIn.lines().collect(Collectors.toList()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
