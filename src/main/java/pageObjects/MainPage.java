package pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class MainPage extends BasicPage {
    protected final static Logger logger = Logger.getLogger(MainPage.class);

    @FindBy(css = "div span.news-filters__item-title")
    private List<WebElement> newsRows;

    @FindBy(xpath = "//ui-heading/span/button")
    private WebElement taxerBtn;

    public MainPage waitForLoading() {
        logger.info("Wait for loading main page");
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("div.news-legend__item-text"), 0));
        Assert.assertTrue(newsRows.size() > 0);
        return this;
    }

    public TaxerPage clickTaxerBtn() {
        logger.info("Click on taxer button");
        taxerBtn.click();
        return new TaxerPage();
    }

}
