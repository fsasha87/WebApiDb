package tests;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utils.DatabaseAccess;

public class DBTest {
    final Logger logger = Logger.getLogger(DBTest.class);

    @Test
    public void getOrderIdByClientIdTest (){
        String clientId = "101204";
        logger.info("**********New Test**********");
        logger.info("Running test is " + Thread.currentThread().getStackTrace()[1].getMethodName());
        logger.info("ClientID is  " + clientId);
        String orderId = new DatabaseAccess().getOrderId(clientId);
        logger.info("OrderId is : " + orderId);
        Assert.assertNotNull(orderId, "There is no such order");
    }

    @Test
    public void getQuantityTest (){
        String productId = "A102";
        logger.info("**********New Test**********");
        logger.info("Running test is " + Thread.currentThread().getStackTrace()[1].getMethodName());
        logger.info("The productId is:  " + productId);
        int quantity = new DatabaseAccess().getQuantityOfProduct(productId);
        logger.info("Quantity of productId "+ productId + " is: " + quantity);
        Assert.assertNotNull(quantity, "There is no availability of productID:" + productId);

    }

    @AfterMethod(alwaysRun = true)
    public void finish(ITestResult result){
        if (result.isSuccess())
            logger.info("The test is passed");
        else
            logger.info("The test is failed");
    }

}
