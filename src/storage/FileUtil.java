package storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public List<String> readAllLines(String filePath) throws IOException {
        Path path = Path.of(filePath);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        return Files.readAllLines(path);
    }

    public void writeLines(String filePath, List<String> lines) throws IOException {
        Files.write(Path.of(filePath), lines);
    }

    public void appendLine(String filePath, String line) throws IOException {
        Path path = Path.of(filePath);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        Files.writeString(path, line + System.lineSeparator(), java.nio.file.StandardOpenOption.APPEND);
    }
}
