package main.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.entities.User;

public class UserService {
    private Map<String, User> users;

    public UserService() {
        this.users = new HashMap<>();
    }

    // Method to add a user
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    // Method to get a user by username
    public User getUserByUsername(String username) {
        return users.get(username);
    }

    // Method to check if a user exists
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    // Method to remove a user
    public void removeUser(String username) {
        users.remove(username);
    }

    // Method to authenticate a user (check username and password)
    public boolean authenticateUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    // Method to retrieve a list of all users
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
    // Additional methods for user management
    public void updateUser(User user) {
        if (userExists(user.getUsername())) {
            users.put(user.getUsername(), user);
        } else {
            // Handle the case when the user does not exist
            throw new IllegalArgumentException("User does not exist.");
        }
    }
}

