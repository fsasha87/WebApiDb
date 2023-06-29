package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WebDriverMultiton;

public class BasicPage {
        WebDriverWait wait = null;

    {
        PageFactory.initElements(WebDriverMultiton.getDriver(), this);
        wait = new WebDriverWait(WebDriverMultiton.getDriver(), 30);
    }

    public WebElement getElement(By locator) {
        WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return webElement;
    }

}
