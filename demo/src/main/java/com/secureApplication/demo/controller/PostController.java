package com.secureApplication.demo.controller;

import com.secureApplication.demo.models.Post;
import com.secureApplication.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    /*
     * displays all posts and the create-post form, user-generated content is rendered without sanitisation
     */
    @GetMapping("/posts")
    public String viewPosts(Model model) {

        model.addAttribute("posts", postRepository.findAll());

        return "posts";
    }

    /*
     * handles post creation, user input is stored directly without validation
     */
    @PostMapping("/posts")
    public String createPost(Post post) {
        postRepository.save(post);

        return "redirect:/posts";
    }

    /*
     * demonstrates reflected XSS via query parameter, it doesn't have real search functionality or frontend flow you have to type it into the browser
     * enter into the browser: http://localhost:8080/search?query=<script>alert('this message propped up because of reflected XSS Vulnerability')</script>
     */
    @GetMapping("/search")
    public String search(@RequestParam String query, Model model) {

        // Reflected user input (intentionally insecure)
        model.addAttribute("query", query);
        model.addAttribute("posts", postRepository.findAll());

        return "posts";
    }
}
