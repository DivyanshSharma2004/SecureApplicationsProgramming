package com.secureApplication.demo.controller;

import com.secureApplication.demo.repository.UserRepository;
import com.secureApplication.demo.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AuthController {
    //removed autowired, easier to test later
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
            model.addAttribute("error", "All fields are required");
            return "register";
        }

        // blank checks
        if (user.getUsername().isBlank() || user.getPassword().isBlank() || user.getEmail().isBlank()) {
            model.addAttribute("error", "All fields must be filled in");
            return "register";
        }

        // email format check
        if (!user.getEmail().contains("@")) {
            model.addAttribute("error", "Invalid email address");
            return "register";
        }

        String userPassword = user.getPassword();//hash password before storing
        user.setPassword(passwordEncoder.encode(userPassword));

        userRepository.save(user);
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

            return "redirect:/posts";
        }

        model.addAttribute("error", "Invalid login details");
        return "login";
    }
}
