package com.secureApplication.demo.repository;

import com.secureApplication.demo.models.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /*
     * Saves a post to the database, user input is stored exactly as entered with no validation or sanitisation
     */
    @Transactional
    public void save(Post post) {
        entityManager.persist(post);
    }

    /*
     * returns all posts from the database, stored XSS scripts will be returned and rendered
     */
    public List<Post> findAll() {

        return entityManager
                .createQuery("SELECT p FROM Post p", Post.class)
                .getResultList();
    }
}
