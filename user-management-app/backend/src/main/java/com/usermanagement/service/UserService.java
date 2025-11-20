package com.usermanagement.service;

import com.usermanagement.model.User;
import com.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ActivityLogService activityLogService;

    // Updated: Added username parameter
    public User registerUser(String firstName, String lastName, String username, String email, String password, String mobileNumber) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username); // Store username
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setMobileNumber(mobileNumber);

        User savedUser = userRepository.save(user);

        // Log activity
        activityLogService.logActivity(savedUser.getId(), "USER_REGISTRATION",
                "User registered: " + email + " (Mobile: " + mobileNumber + ", Username: " + username + ")");

        return savedUser;
    }

    // Updated: Login using username instead of email
    public Optional<User> loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Log successful login
                activityLogService.logActivity(user.getId(), "USER_LOGIN",
                        "User logged in: " + username);
                return Optional.of(user);
            } else {
                // Log failed login attempt
                activityLogService.logActivity(user.getId(), "LOGIN_FAILED",
                        "Failed login attempt for: " + username);
            }
        }

        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Reset password with username and mobile number
    public boolean resetPassword(String username, String mobileNumber, String newPassword) {
        Optional<User> userOptional = userRepository.findByUsernameAndMobileNumber(username, mobileNumber);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            // Log activity
            activityLogService.logActivity(user.getId(), "PASSWORD_RESET",
                    "Password reset for user: " + username + " (Mobile: " + mobileNumber + ")");

            return true;
        }

        return false;
    }
}

