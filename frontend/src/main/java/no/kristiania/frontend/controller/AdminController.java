package no.kristiania.frontend.controller;

import no.kristiania.backend.service.Genres;
import no.kristiania.backend.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import javax.inject.Named;

@Named
@RequestScope
public class AdminController {

    @Autowired
    private MovieService movieService;

    private String currentTitle;
    private String currentDescription;
    private String genre;
    private String currentDirector;



    public String getCurrentTitle() {
        return currentTitle;
    }

    public void setCurrentTitle(String currentTitle) {
        this.currentTitle = currentTitle;
    }

    public String getCurrentDescription() {
        return currentDescription;
    }

    public void setCurrentDescription(String currentDescription) {
        this.currentDescription = currentDescription;
    }

    public String getCurrentCategory() {
        return genre;
    }

    public void setCurrentCategory(String currentCategory) {
        this.genre = currentCategory;
    }

    public String removeItem(Long itemId) {

        movieService.deleteMovie(itemId);
        return "/admin.jsf?faces-redirect=true";
    }
}
