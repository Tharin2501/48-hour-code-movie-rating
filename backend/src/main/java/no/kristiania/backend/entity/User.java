package no.kristiania.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;


/*
    This file is heavily inspired from:
    https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/18f764c3123f60339ab98167790aa223641e7559/intro/spring/security/authorization/src/main/java/org/tsdes/intro/spring/security/authorization/db/UserEntity.java
 */
@NamedQueries({
        @NamedQuery(name = User.GET_ALL_USERS, query = "select user from User user")
})
@Entity
@Table(name = "USERS")
public class User {

    public static final String GET_ALL_USERS = "GET_ALL_USERS";

    @Id
    @NotNull
    @NotBlank
    @Email
    private String email;


    @NotBlank
    @Size(max = 64)
    private String name;

    @NotBlank
    @Size(max = 64)
    private String lastName;

    @NotBlank
    @NotNull
    @Size(max = 255)
    private String password;


    @NotNull
    @OneToMany(mappedBy = "rankId.user")
    private List<Rank> ranks;


    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;


    @NotNull
    private Boolean enabled;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Rank> getRanks() {
        return ranks;
    }

    public void setRanks(List<Rank> ranks) {
        this.ranks = ranks;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
