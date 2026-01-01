package com.secureApplication.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.secureApplication.demo.models.User;

@Repository
public class UserRepository {

    //object used to interact with the database
    @PersistenceContext
    private EntityManager entityManager;

    //TODO:fix this method
    /*
    * intentionally vulnerable to sql injection
    * user input is concacted directly into SQL query string without validation
    *
    * violates Owasp: injection vulnerability
    * */
    public User findUserByUsername(String username) {

        String query =
                "SELECT * FROM user WHERE username = '" + username + "'";

        return (User) entityManager
                .createNativeQuery(query, User.class)
                .getSingleResult();//throws error when more than 1 user is fetched
    }

    //TODO:fix this method
    /*
     * saves a user object to the database.
     * no input validation or password protection is place
     */
    public void save(User user) {
        entityManager.persist(user);
    }
}
