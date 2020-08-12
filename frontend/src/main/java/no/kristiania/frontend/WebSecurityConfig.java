package no.kristiania.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * This file is heavily inspired from:
 * https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/spring/security/authorization/src/main/java/org/tsdes/intro/spring/security/authorization/WebSecurityConfig.java
 */
// Sets up Spring Security, where user info is stored on database
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) {

        try {
            // CSRF Tokens enabled by default.JSF has its own CSRF Token mechanism, so we disable the Spring Security one
            http.csrf().disable();
            /*
                Authorization rules are checked 1 at a time, starting from the top.
                Matching is based on the resource path in the HTTP request, and uses Regex
                We want to allow anyone to access the homepage and the login/signup/logout.
                All other pages are denied unless the user is authenticated.
                Note: login and logout pages are handled specially by Spring Security.
             */
            http.authorizeRequests()
                    .antMatchers("/", "/index.jsf", "/signup.jsf", "/assets/**").permitAll()
                    .antMatchers("/javax.faces.resource/**").permitAll()
                    .antMatchers("/ui/**").authenticated()
                    // Whitelisting: everything not explicitly allowed above gets denied
                    .anyRequest().authenticated()
                    .and()
                    //Login is going to be done with a HTML form, which will encoded using x-www-form-urlencoded
                    .formLogin()
                    // Here, we use our custom page to handle the login
                    .loginPage("/login.jsf")
                    .permitAll()
                    // If login fails, stay on the same page, but add "?error=true" to the URL query param
                    .failureUrl("/login.jsf?error=true")
                    // If login is a success, do a 302 redirect to homepage
                    .defaultSuccessUrl("/index.jsf")
                    .and()
                    /*
                        We do not have a page for Logout.
                        Spring Security will automatically create an endpoint which handles POST on /logout.
                     */
                    .logout()
                    // On logout, do a 302 redirect to homepage
                    .logoutSuccessUrl("/index.jsf");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
/*
    // Tells Spring Security how to access the SQL database to check username/hashed password when trying to auth a user
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {

        try {
            auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery(
                            "SELECT username, password, enabled " +
                                    "FROM users " +
                                    "WHERE username = ?"
                    )
                    .authoritiesByUsernameQuery(
                            "SELECT x.username, y.roles " +
                                    "FROM users x, user_roles y " +
                                    "WHERE x.username = ? and y.user_username = x.username "
                    )
                    .passwordEncoder(passwordEncoder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {

        try {
            auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery(
                            "SELECT email, password, enabled " +
                                    "FROM users " +
                                    "WHERE email = ?"
                    )
                    .authoritiesByUsernameQuery(
                            "SELECT x.email, y.roles " +
                                    "FROM users x, user_roles y " +
                                    "WHERE x.email = ? and y.user_email = x.email "
                    )
                    /*
                        Note: in BCrypt, the "password" field also contains the salt
                     */
                    .passwordEncoder(passwordEncoder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
