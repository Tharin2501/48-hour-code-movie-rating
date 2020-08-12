package no.kristiania.backend.service;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.Rank;
import no.kristiania.backend.entity.RankId;
import no.kristiania.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RankServiceTest extends ServiceTestBase {

    @Autowired
    private RankService rankService;

    @Autowired
    private MovieService movieService;

    @Test
    public void testRankMovie() {

        String userEmail = createTestUser();
        Long movieID = movieService.createMovie("movie", "movie","thriller",2001, Genres.THRILLER);

        Rank before = rankService.getRank(userEmail, movieID);
        assertNull(before);

        User user = userService.getUser(userEmail);
        Movie movie = movieService.getMovie(movieID);

        rankService.rankMovie(movie, user, 3);


        Rank after = rankService.getRank(userEmail, movieID);
        assertNotNull(after);
    }


    @Test
    public void testTooGreatRanking() {

        String userEmail = createTestUser();
        Long movieId = movieService.createMovie("movie", "movie","thriller",2001, Genres.THRILLER);

        User user = userService.getUser(userEmail);
        Movie movie = movieService.getMovie(movieId);

        assertThrows(IllegalArgumentException.class, () -> {
            rankService.rankMovie(movie, user, 10);
        });
    }

    @Test
    public void testTooSmallRanking() {

        String userEmail = createTestUser();
        Long movieId = movieService.createMovie("movie", "movie","thriller",2001, Genres.THRILLER);

        User user = userService.getUser(userEmail);
        Movie movie = movieService.getMovie(movieId);

        assertThrows(IllegalArgumentException.class, () -> {
            rankService.rankMovie(movie, user, 0);
        });
    }

    @Test
    public void testUpdateRating() {

        RankId rankId = createTestRank();

        rankService.updateRating(rankId.getUser().getEmail(), rankId.getMovie().getId(), 3);

        Rank rank = rankService.getRank(rankId);
        assertEquals(Integer.valueOf(3), rank.getRating());
    }


    @Test
    public void testInvalidRating() {

        RankId rankId = createTestRank();

        assertThrows(IllegalArgumentException.class, () -> {

            rankService.updateRating(rankId.getUser().getEmail(), rankId.getMovie().getId(), 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {

            rankService.updateRating(rankId.getUser().getEmail(), rankId.getMovie().getId(), 6);
        });
    }

    @Test
    public void testAvarageRankOfMovie() {

        Movie movie = movieService.getMovie(createTestMovie());

        User user1 = userService.getUser(createTestUser());
        User user2 = userService.getUser(createTestUser());
        User user3 = userService.getUser(createTestUser());

        rankService.rankMovie(movie, user1, 4);
        rankService.rankMovie(movie, user2, 1);
        rankService.rankMovie(movie, user3, 2);

        double average = rankService.getAverageRank(movie.getId());
        assertEquals(2.3333333333333335, average);
    }

    @Test
    public void testCountRank() {

        Movie movie = movieService.getMovie(createTestMovie());

        User user1 = userService.getUser(createTestUser());
        User user2 = userService.getUser(createTestUser());

        rankService.rankMovie(movie, user1, 1);
        rankService.rankMovie(movie, user2, 3);

        long count = rankService.getRankCount(movie.getId());
        assertEquals(2, count);
    }

    @Test
    public void testGetCurrentRating() {

        Movie movie = movieService.getMovie(createTestMovie());

        User user = userService.getUser(createTestUser());

        rankService.rankMovie(movie, user, 3);
        assertEquals(3, rankService.getCurrentRating(movie.getId(), user.getEmail()));

        rankService.rankMovie(movie ,user, 2);
        assertEquals(2, rankService.getCurrentRating(movie.getId(), user.getEmail()));
    }

    @Test
    public void testDeleteRank() {

        Movie movie = movieService.getMovie(createTestMovie());

        User user = userService.getUser(createTestUser());

        RankId rankId = rankService.rankMovie(movie, user, 2);
        assertNotNull(rankService.getRank(rankId));

        rankService.removeRank(user.getEmail(), movie.getId());
        assertNull(rankService.getRank(rankId));
    }

}
