package no.kristiania.backend.service;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.Rank;
import no.kristiania.backend.entity.RankId;
import no.kristiania.backend.entity.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class RankService {

    @PersistenceContext
    private EntityManager entityManager;

    private void checkRating(int rating) {

        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Please set a rating between 1 to 5 stars");
        }
    }

    public Rank getRank(String userEmail, Long movieId) {

        Query query = entityManager.createNamedQuery(Rank.GET_RANK_BY_USER_EMAIL_AND_MOVIE_ID, Rank.class);
        query.setParameter("userEmail", userEmail);
        query.setParameter("movieId", movieId);

        List<Rank> results = (List<Rank>) query.getResultList();
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }

    public Rank getRank(RankId rankId) {

        return entityManager.find(Rank.class, rankId);
    }

    public boolean hasRanked(String userEmail, Long movieId) {

        Rank rank = getRank(userEmail,movieId);
        boolean ranked = rank != null;
        return ranked;
    }

    @Transactional
    public RankId updateRating(String userEmail, Long  movieId, int rating) {

        checkRating(rating);

        Rank rank = (Rank) entityManager.createNamedQuery(Rank.GET_RANK_BY_USER_EMAIL_AND_MOVIE_ID)
                .setParameter("userEmail", userEmail)
                .setParameter("movieId", movieId)
                .getResultList()
                .get(0);

        rank.setRating(rating);
        entityManager.merge(rank);
        return rank.getRankId();
    }

    @Transactional
    public RankId createRank(Movie movie, User user, int rating) {

        RankId rankId = new RankId();
        rankId.setMovie(movie);
        rankId.setUser(user);

        Rank rank = new Rank();
        rank.setRankId(rankId);
        rank.setRating(rating);

        entityManager.persist(rank);
        return rankId;
    }


    @Transactional
    public RankId rankMovie(Movie movie, User user, int rating) {

        checkRating(rating);

        if(hasRanked(user.getEmail(), movie.getId())) {

            return updateRating(user.getEmail(), movie.getId(), rating);
        }

        return createRank(movie, user, rating);
    }

    public double getAverageRank(Long movieId) {

        Query query = entityManager.createNamedQuery(Rank.GET_AVERAGE_RANK_OF_MOVIE, Double.class);
        query.setParameter("movieId", movieId);

        try {
            double result = (Double) query.getSingleResult();
            return result;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public long getRankCount(Long movieId) {

        Query query = entityManager.createNamedQuery(Rank.GET_COUNT_BY_MOVIE_ID, Long.class);
        query.setParameter("movieId", movieId);

        long result = (Long) query.getSingleResult();
        return result;
    }

    @Transactional
    public void removeRank(String userEmail, Long movieId) {

        Rank rank = getRank(userEmail, movieId);
        entityManager.remove(rank);
    }

    public int getCurrentRating(Long movieId, String userEmail) {

        Query query = entityManager.createNamedQuery(Rank.GET_RATING_BY_MOVIE_ID_AND_USER_EMAIL, Integer.class);
        query.setParameter("movieId", movieId);
        query.setParameter("userEmail", userEmail);

        int result = (Integer) query.getSingleResult();
        return result;
    }
}
