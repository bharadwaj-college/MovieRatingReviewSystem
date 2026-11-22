package com.movierating.movieratingreviewsystem.service;

import com.movierating.movieratingreviewsystem.dao.UserDAO;
import com.movierating.movieratingreviewsystem.entity.User;
import com.movierating.movieratingreviewsystem.util.PasswordUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public void addUser(User user) {
        userDAO.addUser(user);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public void deleteUser(int userId) {
        userDAO.deleteUser(userId);
    }

    // Register a new user
    public boolean registerUser(String name, String email, String password)
            throws Exception {

        if (name == null || name.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || password == null || password.length() < 8) {
            return false;
        }

        email = email.trim().toLowerCase();

        if (userDAO.getUserByEmail(email) != null) {
            return false;
        }

        User user = new User();
        user.setName(name.trim());
        user.setEmail(email);
        user.setPasswordHash(PasswordUtil.hashPassword(password));

        userDAO.addUser(user);

        return true;
    }

    // Authenticate an existing user
    public User authenticateUser(String email, String password)
            throws Exception {

        if (email == null || password == null
                || email.trim().isEmpty() || password.isEmpty()) {
            return null;
        }

        User user = userDAO.getUserByEmail(
            email.trim().toLowerCase()
        );

        if (user == null || user.getPasswordHash() == null) {
            return null;
        }

        if (PasswordUtil.verifyPassword(
                password, user.getPasswordHash())) {
            return user;
        }

        return null;
    }
}
