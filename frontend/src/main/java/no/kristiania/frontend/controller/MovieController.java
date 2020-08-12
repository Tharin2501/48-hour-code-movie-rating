package no.kristiania.frontend.controller;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.service.Genres;
import no.kristiania.backend.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.PostConstruct;

import javax.inject.Named;
import java.util.List;

@Named
@RequestScope
public class MovieController {

    @Autowired
    private MovieService movieService;

    private String selectedGenre;

    private Long selectedRating;

    private List<Movie> homePageMovies;

    @PostConstruct
    private void init() {

        homePageMovies = movieService.getMoviesSortedByAverageRating(null);
    }


    public String filterMovies() {

        // i.e. some category was chosen
        if (!selectedGenre.isEmpty()) {
            homePageMovies = movieService.getMoviesSortedByAverageRating(Genres.valueOf(selectedGenre));
        } else {
            homePageMovies = movieService.getMoviesSortedByAverageRating(null);
        }

        return "/search.jsf?faces-redirect=true";
    }


    public String goToMoviePage(Long id) {

        return String.format("movie.jsf?movieId=%d&faces-redirect=true", id);
    }

    public Movie getMovie(Long id) {

        return movieService.getMovie(id);
    }

    public List<Movie> getAllMovies() {

        return movieService.getMoviesSortedByAverageRating(null);
    }

    public List<Movie> getHomePageMovies() {

        return homePageMovies;
    }

    public String getSelectedGenre() {

        return selectedGenre;
    }

    public void setSelectedGenre(String selectedGenre) {

        this.selectedGenre = selectedGenre;
    }


    public List<Genres> getAllCategories() {

        return movieService.getAllGeneres();
    }


}
