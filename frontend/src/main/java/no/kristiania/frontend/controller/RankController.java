package no.kristiania.frontend.controller;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.User;
import no.kristiania.backend.service.MovieService;
import no.kristiania.backend.service.RankService;
import no.kristiania.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

import javax.inject.Named;

@Named
@RequestScope
public class RankController {

    @Autowired
    private RankService rankService;
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;

    @Autowired
    private UserController userController;



    public boolean hasRanked(Long movieId) {

        String userEmail = userController.getEmail();
        return rankService.hasRanked(userEmail, movieId);
    }

    public String removeRank(Long movieId) {

        String userEmail = userController.getEmail();
        rankService.removeRank(userEmail, movieId);
        return String.format("/item.jsf?movieId=%d&faces-redirect=true", movieId);
    }

    public String setNewRank(int rank, Long movieId) {

        String userEmail = userController.getEmail();

        User user = userService.getUser(userEmail);
        Movie movie = movieService.getMovie(movieId);
        rankService.rankMovie(movie, user, rank);

        return String.format("/movie.jsf?movieId=%d&faces-redirect=true", movieId);
    }

    public double getAvarageRank(Long movieId) {

        return rankService.getAverageRank(movieId);
    }

    public long getGetRateCountOf(Long movieId) {

        return rankService.getRankCount(movieId);
    }

    public int getCurrentRank(Long movieId) {

        String userEmail = userController.getEmail();
        return rankService.getCurrentRating(movieId, userEmail);
    }
}
