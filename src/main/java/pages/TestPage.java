package pages;

import org.openqa.selenium.By;
import utils.MainClass;

public class TestPage extends MainClass {
    String linkT = "Macbook, купить макбук, цена Apple Macbook";

    private By searchField = By.id("lst-ib");
    private By searchButton = By.name("btnG");
    private By linkText = By.partialLinkText(linkT);

    public void testGoogleSearch(String text) {
        String title = "Macbook, купить макбук, цена Apple Macbook: Киев, Одесса, Украина - citrus.ua";
        getPage("https://www.google.com.ua/");
        enterText(searchField, text);
        clickOn(searchButton, "search button");
        clickOn(linkText, "Search result");
        super.assertEquals("Compare title", super.getTitle(), title);

    }
}
