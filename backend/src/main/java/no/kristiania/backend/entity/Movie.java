package no.kristiania.backend.entity;

import no.kristiania.backend.service.Genres;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = Movie.GET_ALL_MOVIES, query = "select movie from Movie movie")
})
@Entity
public class Movie {

    public static final String GET_ALL_MOVIES = "GET_ALL_MOVIES";

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max = 55)
    private String title;

    @NotBlank
    @Size(max = 355)
    private String plot;

    @NotBlank
    @Size(max = 255)
    private String director;

    @NotNull
    private Integer yearOfRelease;

    @Enumerated(EnumType.STRING)
    private Genres genre;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "rankId.movie")
    private List<Rank> ranks;

    public Movie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Integer getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(Integer yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public Genres getGenres() {
        return genre;
    }

    public void setGenres(Genres genre) {
        this.genre = genre;
    }


    public List<Rank> getRanks() {
        return ranks;
    }

    public void setRanks(List<Rank> ranks) {
        this.ranks = ranks;
    }
}
