package de.mehrzweckmandala.tommel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileExporter {

    public void exportToFile(String content, String fileName) {
        String userPath = System.getProperty("user.home") + "/Desktop/" + fileName;
        Path path = Path.of(userPath);

        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            Files.write(path, content.getBytes());
            System.out.println("Configuration exported to: " + userPath);
        } catch (IOException e) {
            System.out.println("Error exporting configuration: " + e.getMessage());
        }
    }
}
