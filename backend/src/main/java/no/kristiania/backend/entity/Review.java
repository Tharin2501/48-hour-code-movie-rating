package no.kristiania.backend.entity;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NamedQueries({
        @NamedQuery(
                name = Review.GET_REVIEW_BY_EMAIL_AND_MOVIE_ID,
                query = "select review from Review review where review.rank.rankId.user.email = :userEmail and review.rank.rankId.movie.id = :movieId"
        ),
        @NamedQuery(name = Review.GET_REVIEW_BY_MOVIE, query = "select review from Review review where review.rank.rankId.movie.id = :movieId")
})
@Entity
public class Review {

    public static final String GET_REVIEW_BY_EMAIL_AND_MOVIE_ID = "GET_REVIEW_BY_EMAIL_AND_MOVIE_ID";
    public static final String GET_REVIEW_BY_MOVIE = "GET_REVIEW_BY_MOVIE";

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Rank rank;

    @NotBlank
    @Size(max = 255)
    private String title;

    @NotBlank
    @Size(max = 255)
    private String content;


    public Review() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
