package com.secureApplication.demo.controller;

import com.secureApplication.demo.repository.UserRepository;
import com.secureApplication.demo.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class AuthController {
    //removed autowired, easier to test later
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    //Added logging
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    //constructor injection
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*
     * displays the login page
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /*
     * displays the registration page
     */
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    /*
     * handles user registration.
     * user password is hashed before storing into the database and user inputs are checked to see if they are empty.
     */
    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        //null check
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
            logger.warn("Registration attempt with missing fields");
            model.addAttribute("error", "All fields are required");
            return "register";
        }

        // blank checks
        if (user.getUsername().isBlank() || user.getPassword().isBlank() || user.getEmail().isBlank()) {
            logger.warn("Registration attempt with blank fields");
            model.addAttribute("error", "All fields must be filled in");
            return "register";
        }

        // email format check
        if (!user.getEmail().contains("@")) {
            logger.warn("Registration attempt with invalid email");
            model.addAttribute("error", "Invalid email address");
            return "register";
        }
        logger.info("Registration attempt for username: {}", user.getUsername());
        String userPassword = user.getPassword();//hash password before storing
        user.setPassword(passwordEncoder.encode(userPassword));

        userRepository.save(user);
        logger.info("User registered successfully: {}", user.getUsername());
        return "redirect:/login";
    }

    /*
     * handles user login
     * uses spring data JPA with parameterized queries to prevent SQL injection, no more raw SQL commands
     */
    @PostMapping("/login")
    public String loginUser(User user, Model model) {
        //basic check
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            logger.warn("Login attempt with missing credentials");
            model.addAttribute("error", "Invalid login details");
            return "login";
        }

        Optional<User> foundUser =
                userRepository.findByUsername(user.getUsername());
        //check if user passwords are the same
        if (foundUser.isPresent() &&
                passwordEncoder.matches(
                        user.getPassword(),
                        foundUser.get().getPassword())) {
            logger.info("Successful login for user: {}", user.getUsername());
            return "redirect:/posts";
        }
        logger.warn("Failed login attempt for username: {}", user.getUsername());
        model.addAttribute("error", "Invalid login details");
        return "login";
    }
}
