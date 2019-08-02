package db.java.education.chat.server;

import java.io.*;
import java.util.List;

import static db.java.education.chat.server.Utils.journalToString;

class JournalWriter {
    private String fileName;

    JournalWriter(String fileName) {
        this.fileName = fileName;
    }

    void writeJournal(List<String> journal) {
        try (
                BufferedWriter fileOut = new BufferedWriter(
                        new OutputStreamWriter(
                                new BufferedOutputStream(
                                        new FileOutputStream(
                                                new File(fileName)))))) {
            if (journal.size() > 0) {
                fileOut.write(journalToString(journal));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
