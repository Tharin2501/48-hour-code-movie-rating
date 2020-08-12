# Movie rating web application

This web application is a a movie rating website like IMDb.
The application shows a list of movies, where logged-in users can star (1 to 5) them, and write short reviews.
This was part of an final exam in one of my courses at Høyskolen Kristiania.

The whole project was coded in 48-hours as that was the time limit of this exam

Tasks Completed: I did requirements R1, R2, R3 
and partially R4.

Tasks Not Completed: R4 - R5

Main Technologies

    Java
    SpringBoot + Spring Security
    Maven
    Selenium
    Junit
    Docker
    PostgreSQL

Project Structure

This application is split in 3 maven submodules.

    backend: containing all the @Entity and @Service classes.

    frontend: for JSF controller beans, Spring Security and .xhtml templates.

    report: for aggregated JaCoCo report, and for the OWASP dependency check report.

## Entrypoint

LocalApplicationRunner.java : 
- /backend/src/test/java/no/kristiania/backend/ LocalApplicationRunner.java

## How to Run Application

    mvn clean install to build the application (with tests)
    mvn package - DskipTests (without tests)

    Locate and run the entrypoint LocalApplicationRunner.java

    You can now access the application at localhost:8080

## How to run tests

This application contains both unit tests (Junit) and Integration tests (Selenium)

Run from maven command: mvn clean verify

Run from IDE: Run 'All Tests' with Coverage
Testing Code Coverage

Test coverage report at target/site/jacoco/index.html.

    Total coverage(backend): 26%

Test coverage report at target/site/jacoco-it/index.html.

    Total coverage(backend): 95 %


R5 Extra Features
-
* Filter by Movie Genre
* High testing coverage 
* Admin user
* Profile page: Edit your user data
* Admin page: Delete movies
* Nice frontend


Explanation of functionality and possible point deductions


•	Movie details page: show all reviews and their stars. Give possibility to sort reviews by stars. 

The way I approached this might be/or not be the best way to do this. I am unsure that the word “give possibility” means that I should let the user choose if they want to sort the reviews or not. However, I believe that reviews should be ranged(sorted) by stars automatically. A review should display the highest rating at the top sorted by descending starts. When you post a review and give your rating, the reviews for that movie is sorted automatically
 I decided to just “sort reviews by stars” instead of “give possibility”, none the less I believe that I have showed appropriate competence for this concept
