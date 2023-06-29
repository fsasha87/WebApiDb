package tests;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import utils.WebDriverMultiton;


public class BasicTest {
    protected final static Logger logger = Logger.getLogger(BasicTest.class);


    @AfterMethod(alwaysRun = true)
    public void finish(ITestResult result){
        closeAlert();
        if (result.isSuccess())
            logger.info("The test is passed");
        else
            logger.info("The test is failed");
        WebDriverMultiton.close();
    }

    public static boolean closeAlert() {
        try {
            WebDriverMultiton.getDriver().switchTo().alert().dismiss();
            return true;
        }
        catch (NoAlertPresentException Ex) {
            return false;
        }
    }
}
