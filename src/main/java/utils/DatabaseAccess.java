package utils;

import org.apache.log4j.Logger;
import org.testng.Assert;
import java.sql.*;
import java.util.HashMap;

public class DatabaseAccess {
    protected final static Logger logger = Logger.getLogger(DatabaseAccess.class);

    public Connection dbConnection() {
        HashMap<String, String> map = new HashMap<>();
        ReadExcel readExcel = new ReadExcel("src/main/resources/data.xlsx", "SheetDB");
        map = readExcel.excelDataToMapContains("user", "Sasha");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Could not find the JDBC driver!");
            System.exit(1);
        }
        Connection con = null;
        try {
            con = DriverManager.getConnection(map.get("url"), map.get("username"), map.get("password"));
        } catch (SQLException sqle) {
            System.out.println("Could not connect to database");
            System.exit(1);
        }
        return con;
    }


    public ResultSet getValueFromDB(String query) throws SQLException {
        Connection con = dbConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet;
    }

    /**
     *The method receives order id using client id from DB
     *
     * @param clientId - client Id
     *
     * @return order id
     */
    public String getOrderId(String clientId) {
        logger.info("Getting orderId from orders table");
        String query = "select order_id from orders WHERE client_id = '" + clientId + "'";
        String orderId = null;
        try {
            ResultSet resultSet = getValueFromDB(query);
            resultSet.next();
            orderId = resultSet.getString("order_id");
            resultSet.close();
        } catch (SQLException e) {
            Assert.fail("Can't read order_ID");
        }
        logger.info("OrderId: "+ orderId);
        return orderId;
    }

    public int getQuantityOfProduct(String productId) {
        String query = "Select quantity from products where product_id='"+ productId +"'";
        int quantity = 0;
        try {
            ResultSet resultSet = getValueFromDB(query);
            resultSet.next();
            quantity = resultSet.getInt("quantity");
            resultSet.close();
        } catch (SQLException e) {
            Assert.fail("Incorrect statement");
        }
        return quantity;
    }
}
