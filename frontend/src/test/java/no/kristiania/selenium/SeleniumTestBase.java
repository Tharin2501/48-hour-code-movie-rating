package no.kristiania.selenium;

import no.kristiania.backend.service.MovieService;
import no.kristiania.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/*
NOTE: This file is a heavily adapted version of:
* https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/SeleniumTestBase.java
*/

public abstract class SeleniumTestBase {


    @Autowired
    private MovieService movieService;
    @Autowired
    private UserService userService;

    protected abstract WebDriver getDriver();

    protected abstract String getServerHost();

    protected abstract int getServerPort();

    private static final AtomicInteger counter = new AtomicInteger(0);

    private String getUniqueId() {
        return "foo_SeleniumLocalIT_" + counter.getAndIncrement() + "@mail.com";
    }


    private IndexPO home;


    private IndexPO createNewUser(String email, String givenName, String familyName, String password) {

        home.toStartingPage();

        SignUpPO signup = home.toSignUp();

        IndexPO index = signup.createUser(email, givenName, familyName, password);
        assertNotNull(index);

        return index;
    }

    private IndexPO loginUser(String email, String password) {

        home.toStartingPage();

        LoginPO login = home.toLogin();
        IndexPO index = login.loginUser(email, password);

        assertNotNull(index);
        return index;
    }




    @BeforeEach
    public void initTest() {

        /*
            we want to have a new session, otherwise the tests
            will share the same Session beans
         */
        getDriver().manage().deleteAllCookies();

        home = new IndexPO(getDriver(), getServerHost(), getServerPort());

        home.toStartingPage();

        assertTrue(home.isOnPage(), "Failed to start from Home Page");
    }

    // Verifies that the default movies shows up.
    @Test
    public void testDefaultMovies() {
        assertEquals(8, home.getNumberOfItemsDisplayed());
    }



}
