package no.kristiania.backend.service;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.Rank;
import no.kristiania.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ReviewServiceTest extends ServiceTestBase {

    @Autowired
private ReviewService reviewService;

    @Autowired
    private RankService rankService;

    @Test
    public void testCreateNewReview() {

        Rank rank = rankService.getRank(createTestRank());
        assertNull(reviewService.getReview(rank));

        String userEmail = rank.getRankId().getUser().getEmail();
        Long movieId = rank.getRankId().getMovie().getId();

        reviewService.createReview(userEmail, movieId, "test title", "test content");
        assertNotNull(reviewService.getReview(rank));
    }

    @Test
    public void testUpdateReview() {

        Rank rank = rankService.getRank(createTestRank());
        String userEmail = rank.getRankId().getUser().getEmail();
        Long movieId = rank.getRankId().getMovie().getId();


        String updatedTitle = "updated title";
        String updatedContent = "updatedContent";


        reviewService.createReview(userEmail, movieId, "original title", "original content");
        assertNotEquals(updatedTitle, reviewService.getReview(rank).getTitle());
        assertNotEquals(updatedContent, reviewService.getReview(rank).getContent());

        reviewService.createReview(userEmail, movieId, updatedTitle, updatedContent);
        assertEquals(updatedTitle, reviewService.getReview(rank).getTitle());
        assertEquals(updatedContent, reviewService.getReview(rank).getContent());

    }


    @Test
    public void testGetReviewByMovie() {

        int n = 3;
        Long movieId = createTestMovie();
        Movie movie = movieService.getMovie(movieId);

        for (int i = 0; i < n; i++) {

            String userEmail = createTestUser();

            User user = userService.getUser(userEmail);

            rankService.rankMovie(movie, user, 4);
            reviewService.createReview(userEmail, movieId, "comment title", "comment content");
        }

        assertEquals(n, reviewService.getReviewsByMovie(movieId).size());
    }


}
