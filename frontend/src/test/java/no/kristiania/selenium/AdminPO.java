package no.kristiania.selenium;

import no.kristiania.backend.service.Genres;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class AdminPO extends LayoutPO {

    public AdminPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().equals("Admin");
    }

    public void addItem(String title, String description, Genres genre) {

        Select select = new Select(getDriver().findElement(By.id("categorySelect")));
        
        setText("adminTitleInput", title);
        setText("adminDescriptionInput", description);
        select.selectByVisibleText(genre.toString());

        clickAndWait("adminAddItemButton");
    }

    public void removeMovieAt(int index) {

        WebElement button = getDriver()
                .findElements(By.className("adminRemoveItemButton"))
                .get(index);

        button.click();
        waitForPageToLoad();
    }

}
