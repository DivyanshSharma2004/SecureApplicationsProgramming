package com.secureApplication.demo.models;

import jakarta.persistence.*;

@Table(name = "post")
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    //FIXME: fix this method
    /*
     * Post content is stored exactly as inputed by the user, theres no validation or sanitisation
     * This enables stored XSS attacks if rendered straight into html
     */
    private String content;

    private String author;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
