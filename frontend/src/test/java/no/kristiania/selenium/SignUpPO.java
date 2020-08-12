package no.kristiania.selenium;
import static org.junit.Assert.assertTrue;
/*
NOTE: This file is coped from:
* https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/po/SignUpPO.java
*/

public class SignUpPO extends LayoutPO{

    public SignUpPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {

        return getDriver().getTitle().contains("Sign Up");
    }


    public IndexPO signUP(String userID, String password, String name, String lastName) {
        setText("emailTextId", userID);
        setText("givenNameTextId", name);
        setText("familyNameTextId", lastName);
        setText("passwordTextId", password);
        clickAndWait("signUpBtnId");

        IndexPO indexPO = new IndexPO(this);
        assertTrue(getDriver().getTitle().contains("IMDB: Ratings, Reviews and Where to watch the best movies"));

        return indexPO;
    }

    public IndexPO createUser(String email, String givenName, String familyName, String password){

        setText("emailTextId", email);
        setText("givenNameTextId", givenName);
        setText("familyNameTextId", familyName);
        setText("passwordTextId", password);
        clickAndWait("submitBtnId");

        IndexPO indexPO = new IndexPO(this);
        assertTrue(getDriver().getTitle().contains("IMDB: Ratings, Reviews and Where to watch the best movies"));

        return indexPO;
    }
}
