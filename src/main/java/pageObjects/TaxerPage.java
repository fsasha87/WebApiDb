package pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class TaxerPage extends BasicPage {
    protected final static Logger logger = Logger.getLogger(TaxerPage.class);

    @FindBy(xpath = "//div/input[@class='ng-binding']]")
    private WebElement taxListBtn;

    @FindBy(css = "ul li[name]")
    private List<WebElement> taxList;

    @FindBy(css = "[name='salary']")
    private WebElement salaryField;

    @FindBy(css = "[name='income-tax']")
    private WebElement incomeTaxField;

    @FindBy(css = "[name='social-tax']")
    private WebElement socialTaxField;

    @FindBy(css = "[name='net-salary']")
    private WebElement netSalaryField;


    public TaxerPage clickTaxList() {
        try {
            taxListBtn.click();
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//ul/li"), 0));
        } catch (Exception e) {
            taxListBtn.click();
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//ul/li"), 0));
        }
        return this;
    }

    public TaxerPage selectTaxType(String s) {
        clickTaxList();
        logger.info("Select tax type: " + s);
        By selector = By.cssSelector("li[name='" + s + "']");
        try {
            getElement(selector).click();
        } catch (Exception e) {
            try {
                taxListBtn.click();
                getElement(selector).click();
            }catch (ElementClickInterceptedException e2){
                getElement(selector).click();
            }
        }
        Assert.assertEquals(getTaxType(), s);
        return this;
    }

    public String getTaxType() {
        wait.until(ExpectedConditions.attributeToBeNotEmpty(taxListBtn.findElement(By.cssSelector("input")), "value"));
        String value = taxListBtn.findElement(By.cssSelector("div.clar]")).getText();
        logger.info("Selected tax type is: " + value);
        return value;
    }

    public TaxerPage inputSalaryAmount(String value) {
        logger.info("Input salary amount to taxer: " + value);
        for (char c : value.toCharArray()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            salaryField.sendKeys(String.valueOf(c));
        }
        Assert.assertEquals(getSalaryAmount(), value);
        return this;
    }

    public String getSalaryAmount() {
        String value = salaryField.getAttribute("value");
        logger.info("Salary amount is: " + value);
        return value;
    }

    public TaxerPage inputIncomeTaxRate(String value) {
        logger.info("Income tax rate: " + value);
        incomeTaxField.sendKeys(value);
        Assert.assertEquals(getIncomeTaxRate(), value);
        return this;
    }

    public String getIncomeTaxRate() {
        String value = incomeTaxField.getAttribute("value");
        logger.info("Current income tax rate is: " + value);
        return value;
    }

    public TaxerPage inputSocialTaxRate(String value) {
        logger.info("Social tax rate: " + value);
        socialTaxField.sendKeys(value);
        Assert.assertEquals(getSocialTaxRate(), value);
        return this;
    }

    public String getSocialTaxRate() {
        String value = socialTaxField.getAttribute("value");
        logger.info("Current social tax rate: " + value);
        return value;
    }

    public TaxerPage verifyNetSalaryAmount(String salary, String incomeTaxRate, String socialTaxRate) {
        String value = netSalaryField.getAttribute("value");
        logger.info("Net salary amount is " + value);
        float val = Float.parseFloat(salary);
        if (incomeTaxRate != null)
            val = val - Float.parseFloat(incomeTaxRate) * val / 100;
        if (socialTaxRate != null)
            val = val - Float.parseFloat(socialTaxRate) * val / 100;
        logger.info("Verify net salary amount. Expected value is " + val);
        Assert.assertEquals(Float.parseFloat(value), val, "Net salary amount is unexpected");
        return this;
    }

    public TaxerPage verifyIfTaxListDoesNotContain(String value) {
        wait.until(ExpectedConditions.attributeToBeNotEmpty(taxList.get(0), "name"));
        for (WebElement el : taxList) {
            logger.info(el.getText());
            Assert.assertFalse(el.getText().toLowerCase().contains(value));
        }
        return this;
    }
}
