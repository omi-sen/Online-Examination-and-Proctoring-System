package storage;

import model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserFileHandler {
    private static final Pattern USER_PATTERN = Pattern.compile(
            "\\{\\s*\"id\":\\s*(\\d+),\\s*\"username\":\\s*\"([^\"]+)\",\\s*\"password\":\\s*\"([^\"]+)\",\\s*\"role\":\\s*\"([^\"]+)\"\\s*}"
    );

    public List<User> loadUsers(String filePath) {
        List<User> users = new ArrayList<>();

        try {
            String content = Files.readString(Path.of(filePath));
            Matcher matcher = USER_PATTERN.matcher(content);

            while (matcher.find()) {
                users.add(new User(
                        Integer.parseInt(matcher.group(1)),
                        matcher.group(2),
                        matcher.group(3),
                        matcher.group(4)
                ));
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to read users file.", e);
        }

        return users;
    }
}
