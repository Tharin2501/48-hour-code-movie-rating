package no.kristiania.backend.service;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.Rank;
import no.kristiania.backend.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class ReviewService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RankService rankService;

    @Transactional
    public void createReview(String userEmail, Long itemId, String title, String content) {
        Rank rank = rankService.getRank(userEmail, itemId);
        if (checkReviewed(rank)) {

            updateReview(rank, title, content);
        } else {

            createNewReview(rank, title, content);
        }
    }

    public Review getReview(Rank rank) {

        Query query = entityManager.createNamedQuery(Review.GET_REVIEW_BY_EMAIL_AND_MOVIE_ID, Review.class);
        query.setParameter("userEmail", rank.getRankId().getUser().getEmail());
        query.setParameter("movieId", rank.getRankId().getMovie().getId());

        try {

            return (Review) query.getResultList().get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private void createNewReview(Rank rank, String title, String content) {

        Review comment = new Review();
        comment.setTitle(title);
        comment.setContent(content);
        comment.setRank(rank);

        entityManager.persist(comment);
    }

    private void updateReview(Rank rank, String title, String content) {

        Review comment = getReview(rank);
        comment.setTitle(title);
        comment.setContent(content);

        entityManager.merge(comment);
    }


    private boolean checkReviewed(Rank rank) {

        Review review = getReview(rank);
        return review != null;
    }

    public List<Review> getReviewsByMovie(Long movieId) {

        Query query = entityManager.createNamedQuery(Review.GET_REVIEW_BY_MOVIE, Review.class);
        query.setParameter("movieId", movieId);

        return  query.getResultList();
    }

    private List<Movie> getAllMovies() {

        Query query = entityManager.createNamedQuery(Review.GET_REVIEW_BY_MOVIE, Movie.class);
        return query.getResultList();
    }


}
