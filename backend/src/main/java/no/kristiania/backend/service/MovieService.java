package no.kristiania.backend.service;

import no.kristiania.backend.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RankService rankService;

    @Autowired
    private UserService userService;

    @Transactional
    public Long createMovie(String title, String plot, String director, Integer yearOfRelease, Genres genres) {

        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setPlot(plot);
        movie.setDirector(director);
        movie.setYearOfRelease(yearOfRelease);
        movie.setGenres(genres);
        movie.setRanks(new ArrayList<>());

        entityManager.persist(movie);
        return movie.getId();
    }


    @Transactional
    public void deleteMovie(Long movieId) {

        Movie movie = entityManager.find(Movie.class, movieId);

        movie.getRanks().forEach(rank -> {

            if(rank.getReview() != null)
                entityManager.remove(rank.getReview());
            entityManager.remove(rank);
        });

        entityManager.remove(movie);
    }

    public Movie getMovie(Long movieId) {

        return entityManager.find(Movie.class, movieId);
    }

    public List<Movie> getMoviesSortedByAverageRating(Genres genre) {

        return getAllMovie().stream()
                .filter(movie -> {
                    if(genre != null) {
                        return movie.getGenres().equals(genre);
                    }
                    return true;
                })
                .sorted((first, second) -> {

                    Double firstRating = rankService.getAverageRank(first.getId());
                    Double secondRating = rankService.getAverageRank(second.getId());
                    return secondRating.compareTo(firstRating);
                })
                .collect(Collectors.toList());
    }



    private List<Movie> getAllMovie() {

        Query query = entityManager.createNamedQuery(Movie.GET_ALL_MOVIES, Movie.class);
        return query.getResultList();
    }

    public List<Genres> getAllGeneres() {

        return Arrays.stream(Genres.values())
                .collect(Collectors.toList());
    }


}
