package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TaxerTestAPI {
    //dependency: org.json : json
    final Logger logger = Logger.getLogger(TaxerTestAPI.class);

    @DataProvider(name = "dp1")
    public Object[][] dataSupplier() throws IOException {
        File file = new File("src/main/resources/data.xlsx");
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheet("SheetAPI");
        wb.close();
        //int lastRowNum = sheet.getLastRowNum();
        int lastRowNum = sheet.getPhysicalNumberOfRows()-1;
        int lastCellNum = sheet.getRow(0).getLastCellNum();
        Object[][] array = new Object[lastRowNum][1];
        for (int i = 0; i < lastRowNum; i++) {
            Map<Object, Object> map = new HashMap<Object, Object>();
            for (int j = 0; j < lastCellNum; j++) {
                map.put(sheet.getRow(0).getCell(j).toString(), sheet.getRow(i+1).getCell(j).toString());
            }
            array[i][0] = map;
        }
        return array;
    }

    @Test(dataProvider = "dp1")
    public void postEmployeeTest(HashMap<String, Object> excelData) {
        logger.info("****************New Test*****************");
        logger.info("Name is  " + excelData.get("name").toString());
        logger.info("Salary is  " + excelData.get("salary").toString());
        RestAssured.baseURI = excelData.get("url").toString();

        RequestSpecification request = RestAssured.given();

        Response response = request.given()
                .header("Content-type", "application/json")
                .header("Authorization", excelData.get("token").toString())
                .and()
                .body(getRequestBody(excelData).toString())
                .post("/api/point");

        logger.info("Status Code " + response.getStatusCode());
        logger.info("Response Body:" +response.asString());//{"result":true}

        int StatusCode = response.getStatusCode();
        Assert.assertEquals(StatusCode, 200);
        Boolean statusResponse = response.jsonPath().get("result");
        String resultResponse = String.valueOf(statusResponse);

        logger.info("Actual response value is " + resultResponse);
        logger.info("Expected output value is " + excelData.get("expected_value").toString());

        Assert.assertEquals(resultResponse, excelData.get("expected_value").toString());
    }

    public JSONObject getRequestBody(HashMap<String, Object> map) {

        JSONObject requestParams = new JSONObject();
        JSONObject taxRequest = new JSONObject();

        taxRequest.put("salary", Integer.parseInt(map.get("salary").toString()))
                .put("income-tax-rate", Integer.parseInt(map.get("income_tax_rate").toString()))
                .put("social_tax_rate", Integer.parseInt(map.get("social_tax_rate").toString()));

        requestParams.put("name", map.get("name").toString())
                .put("tax-request", taxRequest);
//        System.out.println(requestParams);//{"tax-request":{"salary":2500,"income-tax-rate":25,"social_tax_rate":30},"username":"John Smit"}
        return requestParams;

    }
}
