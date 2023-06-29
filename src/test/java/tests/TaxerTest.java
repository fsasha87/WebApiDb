package tests;

import org.apache.log4j.Logger;
import org.testng.annotations.*;
import pageObjects.LoginPage;
import utils.*;

import java.util.HashMap;

public class TaxerTest extends BasicTest {
    protected final static Logger logger = Logger.getLogger(TaxerTest.class);
    ReadExcel excelReader = new ReadExcel("src/main/resources/data.xlsx", "SheetWeb");
    HashMap<String, String> excelMap = new HashMap();

    @BeforeClass(alwaysRun = true)
    public void readCredentials() {
        excelMap = excelReader.excelDataToMapContains("email", "test2023@gmail.com");
    }

    @BeforeMethod
    public void start() {
        WebDriverMultiton.getDriver().get(excelMap.get("url"));

    }

    @Test
    public void verifyNetSalaryAfterIncomeTax() {
        logger.info("**Running test is " + Thread.currentThread().getStackTrace()[1].getMethodName());
        new LoginPage()
                .login(excelMap.get("email"), excelMap.get("password"))
                .waitForLoading()
                .clickTaxerBtn()
                .selectTaxType(excelMap.get("tax_type"))
                .inputSalaryAmount(excelMap.get("salary"))
                .inputIncomeTaxRate(excelMap.get("income_tax_rate"))
                .verifyNetSalaryAmount(excelMap.get("salary"), excelMap.get("income_tax_rate"), null);

    }

    @Test
    public void verifyNetSalaryAfterSocialTax() {
        logger.info("**Running test is " + Thread.currentThread().getStackTrace()[1].getMethodName());
        new LoginPage()
                .login(excelMap.get("email"), excelMap.get("password"))
                .waitForLoading()
                .clickTaxerBtn()
                .selectTaxType(excelMap.get("tax_type"))
                .inputSalaryAmount(excelMap.get("salary"))
                .inputSocialTaxRate(excelMap.get("social_tax_rate"))
                .verifyNetSalaryAmount(excelMap.get("salary"), null, excelMap.get("social_tax_rate"));
    }

    @Test
    public void verifyNetSalaryAfterIncomeAndSocialTaxes() {
        logger.info("**Running test is " + Thread.currentThread().getStackTrace()[1].getMethodName());
        new LoginPage()
                .login(excelMap.get("email"), excelMap.get("password"))
                .waitForLoading()
                .clickTaxerBtn()
                .selectTaxType(excelMap.get("tax_type"))
                .inputSalaryAmount(excelMap.get("salary"))
                .inputIncomeTaxRate(excelMap.get("income_tax_rate"))
                .inputSocialTaxRate(excelMap.get("social_tax_rate"))
                .verifyNetSalaryAmount(excelMap.get("salary"), excelMap.get("income_tax_rate"), excelMap.get("social_tax_rate"));
    }

    @Test
    public void loanList() {
        logger.info("**Running test is " + Thread.currentThread().getStackTrace()[1].getMethodName());
        new LoginPage()
                .login(excelMap.get("email"), excelMap.get("password"))
                .waitForLoading()
                .clickTaxerBtn()
                .clickTaxList()
                .verifyIfTaxListDoesNotContain("demo");
    }
}
