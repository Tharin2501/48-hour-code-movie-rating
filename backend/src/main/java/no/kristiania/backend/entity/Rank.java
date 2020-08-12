package no.kristiania.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@NamedQueries({
        @NamedQuery(name = Rank.GET_RANK_BY_USER_EMAIL_AND_MOVIE_ID, query = "select rank from Rank rank where rank.rankId.user.email = :userEmail and rank.rankId.movie.id = :movieId"),
        @NamedQuery(name = Rank.GET_AVERAGE_RANK_OF_MOVIE, query = "select avg(rank.rating) from Rank rank where rank.rankId.movie.id = :movieId"),
        @NamedQuery(name = Rank.GET_COUNT_BY_MOVIE_ID, query = "select count(rank) from Rank rank where rank.rankId.movie.id = :movieId"),
        @NamedQuery(name = Rank.GET_RATING_BY_MOVIE_ID_AND_USER_EMAIL, query = "select rank.rating from Rank rank where rank.rankId.movie.id = :movieId and rank.rankId.user.email = :userEmail")
})
@Entity
public class Rank {

    public static final String GET_RANK_BY_USER_EMAIL_AND_MOVIE_ID = "GET_RANK_BY_USER_EMAIL_AND_MOVIE_ID";
    public static final String GET_AVERAGE_RANK_OF_MOVIE = "GET_AVERAGE_RANK_OF_MOVIE";
    public static final String GET_COUNT_BY_MOVIE_ID = "GET_COUNT_BY_MOVIE_ID";
    public static final String GET_RATING_BY_MOVIE_ID_AND_USER_EMAIL = "GET_RATING_BY_MOVIE_ID_AND_USER_EMAIL";

    @EmbeddedId
    private RankId rankId;

    @Min(1)
    @Max(5)
    private Integer rating;

    @OneToOne(mappedBy = "rank")
    private Review review;

    public Rank() {
    }


    public RankId getRankId() {
        return rankId;
    }

    public void setRankId(RankId rankId) {
        this.rankId = rankId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
