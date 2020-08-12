package no.kristiania.backend.service;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.RankId;
import no.kristiania.backend.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class ServiceTestBase {

    @Autowired
    public ResetService resetService ;

    @Autowired
    public MovieService movieService;

    @Autowired
    public RankService rankService;

    @Autowired
    public UserService userService;

    @BeforeEach
    public void cleanDatabase(){
        resetService.resetDatabase();
    }

    protected String createUniqueId(){

        int id = new Random().nextInt();
        return "foo_UserServiceTest_" + id + "@test.com";
    }

    protected String createTestUser() {

        String email = createUniqueId();
        String name = "test";
        String lastName = "tester";
        String password = "123";

        boolean created = userService.createUser(email, name, lastName, password);

        if (!created) {
            return null;
        }
        return email;
    }

    public Long createTestMovie() {

        String title = "test title";
        String plot = "test plot";
        String director = "test dicretor";
        Integer yearOfRelease = 2001;
        Genres genre = Genres.THRILLER;

        return movieService.createMovie(title, plot,director,yearOfRelease, genre);
    }

    protected RankId createTestRank() {

        Movie movie = movieService.getMovie(createTestMovie());

        User user = userService.getUser(createTestUser());
        int rank = 1;

        return rankService.createRank(movie, user, rank);
    }

}
