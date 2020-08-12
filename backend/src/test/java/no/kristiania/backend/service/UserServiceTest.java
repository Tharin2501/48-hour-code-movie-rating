package no.kristiania.backend.service;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.User;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceTest extends ServiceTestBase {

    @Test
    public void testCanCreateUser() {

        String email = "test@mail.com";
        String givenName = "test user given name";
        String familyName = "test user family name";
        String password = "super-secret-password";

        userService.createUser(email, givenName, familyName, password);

        User user = userService.getUser(email);

        assertEquals(email, user.getEmail());
        assertEquals(givenName, user.getName());
        assertEquals(familyName, user.getlastName());
    }

    @Test
    public void testDefaultUserRoles() {


        User user = userService.getUser(createTestUser());
        assertTrue(user.getRoles().contains("USER"));
        assertFalse(user.getRoles().contains("ADMIN"));
    }

    @Test
    public void testCreateAdminUser() {

        String email = "admin@test.com";
        userService.createUser(email, "admin", "admin", "123", "ADMIN");

        User user = userService.getUser(email);

        assertTrue(user.getRoles().contains("ADMIN"));
    }

    @Test
    public void testSameId() {

        String email = createUniqueId();

        boolean created = userService.createUser(email,"test", "tester", "123");

        assertTrue(created);

        created = userService.createUser(email,"ghjkd", "abc", "456");
        assertFalse(created);
    }

    @Test
    public void testRankCount() {

        String email = createTestUser();
        assertEquals(0, userService.getRankCount(email));

        User user = userService.getUser(email);

        Movie movie1 = movieService.getMovie(createTestMovie());
        Movie movie2 = movieService.getMovie(createTestMovie());
        Movie movie3 = movieService.getMovie(createTestMovie());
        Movie movie4 = movieService.getMovie(createTestMovie());

        rankService.rankMovie(movie1, user, 1);
        rankService.rankMovie(movie2, user, 2);
        rankService.rankMovie(movie3, user, 3);
        rankService.rankMovie(movie4, user, 3);

        assertEquals(4, userService.getRankCount(email));
    }

    @Test
    public void testInvalidEmail() {

        String invalidEmail = "NOT_AN_EMAIL";
        assertThrows(Exception.class, () -> {
            userService.createUser(invalidEmail, "test", "tester", "123", "USER");
        });
    }

    @Test
    public void testSameUserCreatedTwice() {

        String email = "twice@email.com";

        boolean first = userService.createUser(email, "admin given", "admin family", "password", "ADMIN");
        boolean second = userService.createUser(email, "admin given", "admin family", "password", "ADMIN");

        assertTrue(first);
        assertFalse(second);
    }

    @Test
    public void testUpdateUser() {

        String name = "name";
        String lastName = "lastName";

        String email = createTestUser();


        User before = userService.getUser(email);
        assertNotEquals(name, before.getName());
        assertNotEquals(lastName, before.getlastName());

        userService.updateUser(email, name, lastName);

        User after = userService.getUser(email);
        assertEquals(name, after.getName());
        assertEquals(lastName, after.getlastName());
    }

    @Test
    public void testCanGetAllUsers() {

        int n = 5;

        for (int i = 0; i < n; i++) {
            createTestUser();
        }

        assertEquals(n, userService.getAllUsers().size());
    }







}
