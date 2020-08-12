package no.kristiania.backend.service;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.function.Supplier;

/**
 * This file is heavily inspired by:
 * https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/734b4a660d8e1d815e2600c0e84cea5920bc5572/intro/exercise-solutions/quiz-game/part-11/backend/src/main/java/org/tsdes/intro/exercises/quizgame/backend/service/DefaultDataInitializerService.java
 */

@Service
public class DefaultDataInitializerService {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private RankService rankService;


    @PostConstruct
    public void initialize() {

        // Test user
        attempt(() ->   attempt(() -> userService.createUser("admin@mail.com", "admin", "admington", "admin", "ADMIN")));


        // Users
        attempt(() -> userService.createUser("kaneki@email.com", "Kaneki", "Ken", "TokyoGhoul1"));
        attempt(() -> userService.createUser("touka@email.com", "Touka", "Kirishima", "TokyoGhoul2"));
        attempt(() -> userService.createUser("shuu@email.com", "Shuu", "Tsukiyama", "TokyoGhoul3"));

        // Movies

        Long movie1 = attempt(() -> movieService.createMovie("Interstellar", "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival. ", "Christopher Nolan",2013, Genres.SCI_FI));
        Long movie2 = attempt(() -> movieService.createMovie("2001: A Space Odyssey", "After discovering a mysterious artifact buried beneath the Lunar surface, mankind sets off on a quest to find its origins with help from intelligent supercomputer H.A.L. 9000.", "Christopher Nolan",1994, Genres.SCI_FI));
        Long movie3 = attempt(() -> movieService.createMovie("Your Name.", "Two strangers find themselves linked in a bizarre way. When a connection forms, will distance be the only thing to keep them apart? ","Makoto Shinkai",2019, Genres.ANIMATION));
        Long movie4 = attempt(() -> movieService.createMovie("Spirited Away", "During her family's move to the suburbs, a sullen 10-year-old girl wanders into a world ruled by gods, witches, and spirits, and where humans are changed into beasts.","Hayo Miyazaki",2006, Genres.ANIMATION));
        Long movie5 = attempt(() -> movieService.createMovie("James Bond: No Time To Die", "James Bond has left active service. His peace is short-lived when Felix Leiter, an old friend from the CIA, turns up asking for help, leading Bond onto the trail of a mysterious villain armed with dangerous new technology.","Cary Joji Fukunaga ",2020, Genres.ACTION));
        Long movie6 = attempt(() -> movieService.createMovie("John Wick", "An ex-hit-man comes out of retirement to track down the gangsters that killed his dog and took everything from him.","Chad Stahelski",2018, Genres.ACTION));
        Long movie7 = attempt(() -> movieService.createMovie("The Lord of the Rings: The Return of the King", "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.","Stephen King",2003, Genres.FANTASY));
        Long movie8 = attempt(() -> movieService.createMovie("Harry Potter and the Deathly Hallows: Part 2", "Harry, Ron, and Hermione search for Voldemort's remaining Horcruxes in their effort to destroy the Dark Lord as the final battle rages on at Hogwarts.","David Yates", 2014, Genres.FANTASY));

        User kaneki = userService.getUser("kaneki@email.com");
        User touka = userService.getUser("touka@email.com");
        User shuu = userService.getUser("shuu@email.com");

        Movie interstellar = movieService.getMovie(movie1);
        Movie spaceOdyssey = movieService.getMovie(movie2);
        Movie yourName = movieService.getMovie(movie3);
        Movie spiritedAway = movieService.getMovie(movie4);
        Movie johnWick = movieService.getMovie(movie5);
        Movie jamesBond = movieService.getMovie(movie6);
        Movie lotr = movieService.getMovie(movie7);
        Movie harryPotter = movieService.getMovie(movie8);

        // Rank
        attempt(() -> rankService.rankMovie(interstellar, kaneki, 5));
        attempt(() -> rankService.rankMovie(interstellar, shuu, 4));
        attempt(() -> rankService.rankMovie(spaceOdyssey, touka, 2));
        attempt(() -> rankService.rankMovie(yourName, shuu, 3));
        attempt(() -> rankService.rankMovie(spiritedAway, kaneki, 5));
        attempt(() -> rankService.rankMovie(johnWick, touka, 3));
        attempt(() -> rankService.rankMovie(jamesBond, kaneki, 3));
        attempt(() -> rankService.rankMovie(lotr, shuu, 4));
        attempt(() -> rankService.rankMovie(harryPotter, kaneki, 2));

        // Review
        reviewService.createReview(
                kaneki.getEmail(),
                interstellar.getId(),
                "Probably the best modern day sci-fi movie",
                "Interstellar is a movie like no other. Unlike many apocalyptic sci-fi films that feature advanced technology as the source of our destruction (ala The Terminator movies), it instead asserts that technology will save us."
        );
        reviewService.createReview(
                touka.getEmail(),
                spaceOdyssey.getId(),
                "Just too boring, im too young for this",
                "There are scenes in the film where next to nothing happens and it has an extremely slow build up to the “climax” of the film"
        );
        reviewService.createReview(
                shuu.getEmail(),
                yourName.getId(),
                "A Beautiful Heartwarming Story - With A Social Conscience ",
                "In a world dominated by stupid sequels, prequels and remakes it is film/anime like this that restores hope in today's entertainment."
        );
        reviewService.createReview(
                kaneki.getEmail(),
                spiritedAway.getId(),
                "Just too boring, im too young for this",
                "Despite an overlong run time, Spirited Away is a visually stunning film filled with some of the most imaginative characters I've ever seen. In addition to the visuals, the music is beautiful, and the story has something for audience members of all ages."
        );
        reviewService.createReview(
                touka.getEmail(),
                johnWick.getId(),
                "Manly action film",
                "Lesson learned: don't get on retired legendary hit-man John Wick's bad side."
        );
        reviewService.createReview(
                kaneki.getEmail(),
                jamesBond.getId(),
                "I love corona",
                "This aint out yet"
        );
        reviewService.createReview(
                shuu.getEmail(),
                lotr.getId(),
                "A Brilliant Conclusion",
                "As a film though, this is amazing. A true lasting legacy in story telling and now cinema. Bravo Mr. Jackson."
        );
        reviewService.createReview(
                kaneki.getEmail(),
                spiritedAway.getId(),
                "Epic End",
                "Even though I consider myself to be a huge HP fan I never thought I would actually give a movie from this series straight 10 out of 10."
        );
    }


    private <T> T attempt(Supplier<T> lambda) {
        try {
            return lambda.get();
        } catch (Exception e) {
            return null;
        }
    }
}
