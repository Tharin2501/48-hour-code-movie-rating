package no.kristiania.backend.service;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Taken from:
 * https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/spring/security/authorization/src/main/java/org/tsdes/intro/spring/security/authorization/db/UserService.java
 */
@Service
@Transactional
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MovieService movieService;

    public boolean createUser(String email, String name, String lastName, String password) {

        return createUser(email, name, lastName, password, "USER");
    }

    public boolean createUser(String email, String name, String lastName, String password, String role) {

        String hashedPassword = passwordEncoder.encode(password);

        if (entityManager.find(User.class, email) != null) {
            return false;
        }

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setlastName(lastName);
        user.setPassword(hashedPassword);
        user.setRanks(new ArrayList<>());
        user.setRoles(Collections.singleton(role));
        user.setEnabled(true);

        entityManager.persist(user);
        return true;
    }

    public User getUser(String email) {

        User user = entityManager.find(User.class, email);
        return user;
    }


    public int getRankCount(String email) {

        return getUser(email)
                .getRanks()
                .size();
    }

    public void updateUser(String email, String name, String lastName) {

        User user = getUser(email);
        user.setName(name);
        user.setlastName(lastName);

        entityManager.merge(user);
    }

    public List<User> getAllUsers() {

        Query query = entityManager.createNamedQuery(User.GET_ALL_USERS, User.class);
        return query.getResultList();
    }


}
