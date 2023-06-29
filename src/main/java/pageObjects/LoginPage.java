package pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import utils.WebDriverMultiton;

public class
LoginPage extends BasicPage{
    protected final static Logger logger = Logger.getLogger(LoginPage.class);

    @FindBy(id = "email")
    WebElement emailField;

    @FindBy(id = "password']")
    WebElement passField;

    @FindBy(css =  "div.identity-submit")
    WebElement submitBtn;


    private LoginPage waitForLoading() {
        logger.info("Wait for loading login page");
        wait.until(ExpectedConditions.visibilityOf(emailField));
        wait.until(ExpectedConditions.visibilityOf(passField));
        wait.until(ExpectedConditions.visibilityOf(submitBtn));
        return this;
    }

    public LoginPage enterEmail(String email) {
        logger.info("Enter email");
        emailField.sendKeys(email);
        Assert.assertEquals(emailField.getAttribute("value"), email, "email is not as expected");
        return this;
    }

    public LoginPage enterPass(String pass) {
        logger.info("Enter password");
        passField.sendKeys(pass);
        Assert.assertEquals(passField.getAttribute("value"), pass, "password is not as expected");
        return this;
    }


    public LoginPage clickSubmit() {
        logger.info("Click on submit button");
        submitBtn.click();
        return this;
    }

    /**
     *The method pass authorization on login page
     * @param email - email
     * @param pass - pass
     * @return main page
     */
    public MainPage login(String email, String pass){
        waitForLoading();
        Assert.assertEquals(WebDriverMultiton.getDriver().getTitle(), "Login Page", "Login page is not loaded");
        enterEmail(email);
        enterPass(pass);
        clickSubmit();
        return new MainPage();
    }

}
