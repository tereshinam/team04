package db.java.education.chat.server;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static db.java.education.chat.server.Utils.journalToString;

public class JournalWriter {
    private String fileName;

    public JournalWriter(String fileName) {
        this.fileName = fileName;
    }

    public void writeJournal(List<String> journal){
        try (
                BufferedWriter fileOut = new BufferedWriter(
                        new OutputStreamWriter(
                                new BufferedOutputStream(
                                        new FileOutputStream(
                                                new File(fileName), true))))) {
            if (journal.size() > 0) {
                fileOut.write(journalToString(journal));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
