package no.kristiania.frontend.controller;

import no.kristiania.backend.entity.Review;
import no.kristiania.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

import javax.inject.Named;
import java.util.List;

@Named
@RequestScope
public class ReviewController {


    @Autowired
    private UserController userController;

    @Autowired
    private ReviewService reviewService;


    private String currentTitle;
    private String currentContent;
   

    public String getCurrentTitle() {
        return currentTitle;
    }

    public void setCurrentTitle(String currentTitle) {
        this.currentTitle = currentTitle;
    }

    public String getCurrentContent() {
        return currentContent;
    }

    public void setCurrentContent(String currentContent) {
        this.currentContent = currentContent;
    }

    public String createNewReview(Long movieId) {

        String userEmail = userController.getEmail();
        String url = String.format("/item.jsf?movieId=%d&faces-redirect=true", movieId);
        if(currentTitle.isEmpty() || currentContent.isEmpty()) {
            return url + "&error=true";
        }

        reviewService.createReview(userEmail, movieId, currentTitle, currentContent);
        return url;
    }

    public List<Review> getReviewByMovie(Long movieId) {

        return reviewService.getReviewsByMovie(movieId);
    }
}
