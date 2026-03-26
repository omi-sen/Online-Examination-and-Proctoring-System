package service;

import exception.LoginFailedException;
import model.User;
import storage.UserFileHandler;
import util.Constants;

import java.util.List;

public class AuthService {
    private final UserFileHandler userFileHandler;

    public AuthService() {
        this.userFileHandler = new UserFileHandler();
    }

    public User login(String username, String password, String role) throws LoginFailedException {
        List<User> users = userFileHandler.loadUsers(Constants.USERS_FILE);

        for (User user : users) {
            boolean usernameMatch = user.getUsername().equalsIgnoreCase(username.trim());
            boolean passwordMatch = user.getPassword().equals(password);
            boolean roleMatch = user.getRole().equalsIgnoreCase(role);

            if (usernameMatch && passwordMatch && roleMatch) {
                return user;
            }
        }

        throw new LoginFailedException("Invalid username, password, or role.");
    }
}
