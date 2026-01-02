package com.secureApplication.demo.controller;

import com.secureApplication.demo.repository.UserRepository;
import com.secureApplication.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    //TODO: fix this method
    /*
     * displays the login page.(gets over rided by springs default login page as of this state, have to edit secuirtyconfig)
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

    //TODO: fix this method
    /*
     * handles user registration.
     * user details are stored directly into the database without validation or password hashing (intentionally insecure).
     */
    @PostMapping("/register")
    public String registerUser(User user) {

        userRepository.save(user);

        return "redirect:/login";
    }

    //TODO: fix this method
    /*
     * handles user login
     * uses an insecure repository method vulnerable to SQL Injection Exceptions are caught broadly and not handled properly.
     */
    @PostMapping("/login")
    public String loginUser(User user, Model model) {

        try {
            User foundUser =
                    userRepository.findUserByUsername(user.getUsername());//insecure as it uses insecure findUserByUsername method that uses concacted input

            if (foundUser.getPassword().equals(user.getPassword())) {
                return "redirect:/posts";
            }

        } catch (Exception e) {
            //ignores all exceptions, intentionally vulnerable
        }

        model.addAttribute("error", "Invalid login details");
        return "login";
    }
}
