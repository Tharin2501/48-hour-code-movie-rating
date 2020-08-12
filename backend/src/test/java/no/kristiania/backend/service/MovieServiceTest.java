package no.kristiania.backend.service;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.RankId;
import no.kristiania.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MovieServiceTest extends ServiceTestBase {

    @Test
    public void testMoviesSortedByAverageRanking() {

        Movie popular = movieService.getMovie(createTestMovie());
        Movie moderate = movieService.getMovie(createTestMovie());
        Movie unpopular = movieService.getMovie(createTestMovie());

        for (int i = 0; i < 5; i++) {

            User user = userService.getUser(createTestUser());
            rankService.rankMovie(popular, user, 5);
        }

        for (int i = 0; i < 3; i++) {

            User user = userService.getUser(createTestUser());
            rankService.rankMovie(moderate, user, 3);
        }

        List<Movie> movies = movieService.getMoviesSortedByAverageRating(null);

        assertEquals(3, movies.size());
        assertEquals(popular.getId(), movies.get(0).getId());
        assertEquals(unpopular.getId(), movies.get(2).getId());
    }


    @Test
    public void testFilterByGenre() {

        movieService.getMovie(
                movieService.createMovie("test action 1", "action 1","action 1",2001, Genres.ACTION)
        );
        movieService.getMovie(
                movieService.createMovie("test action 2", "action 2","action 1",2001, Genres.ACTION)
        );
        movieService.getMovie(
                movieService.createMovie("thriller", "thriller","thriller ",2002, Genres.THRILLER)
        );



        List<Movie> allMovies = movieService.getMoviesSortedByAverageRating(null);
        assertEquals(3, allMovies.size());

        List<Movie> filtered = movieService.getMoviesSortedByAverageRating(Genres.ACTION);
        assertEquals(2, filtered.size());
        filtered.forEach(item -> assertEquals(Genres.ACTION, item.getGenres()));
    }

    @Test
    public void testReturnAllGenres() {

        List<Genres> expected = Arrays.stream(Genres.values()).collect(Collectors.toList());
        List<Genres> actual = movieService.getAllGeneres();

        actual.forEach(genre -> assertTrue(expected.contains(genre)));
    }

    @Test
    public void testDeleteMovie() {

        Long id = createTestMovie();
        assertNotNull(movieService.getMovie(id));

        movieService.deleteMovie(id);
        assertNull(movieService.getMovie(id));
    }

}
