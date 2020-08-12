package no.kristiania.frontend.controller;

import no.kristiania.backend.entity.User;
import no.kristiania.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/*
    This file is heavily inspired from:

 */
@Named
@RequestScoped
public class UserController {

    @Autowired
    private UserService userService;

    private String currentName;
    private String currentLastName;

    public String getEmail(){
        return getUserDetails().getUsername();
    }

    public String getFullName() {

        User user = userService.getUser(getEmail());
        return user.getName() + " " + user.getlastName();
    }

    public int getRankCount() {

        return userService.getRankCount(getEmail());
    }

    private UserDetails getUserDetails() {

        return (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }


    public String update() {

        String url = "/profile.jsf?faces-redirect=true";
        if (currentName.isEmpty() || currentLastName.isEmpty()) {
            return url + "&error=true";
        }

        String email = getEmail();
        userService.updateUser(email, currentName, currentLastName);

        return url;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

    public String getCurrentLastName() {
        return currentLastName;
    }

    public void setCurrentLastName(String currentLastName) {
        this.currentLastName = currentLastName;
    }
}
