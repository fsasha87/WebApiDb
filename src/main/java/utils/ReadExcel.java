package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class ReadExcel {
    Workbook excelWbook;
    XSSFSheet excelWSheet;
    XSSFCell cell;

    ReadExcel(){ }

    public ReadExcel(String path, String sheetName){
        try {
            excelWbook = new XSSFWorkbook(new FileInputStream(path));
            excelWSheet = (XSSFSheet) excelWbook.getSheet(sheetName);
        } catch (IOException e) {
            Assert.fail("Exception " + e.getMessage());
        }
    }

    public int countRows() {
        return excelWSheet.getLastRowNum();
    }

    private String getCellData(int rowNum, int colNum) {
        cell = excelWSheet.getRow(rowNum).getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        DataFormatter formatter = new DataFormatter();
        String value = formatter.formatCellValue(cell);
        return value;
    }

    private String[] getRowData(int rowNo) {
        int startCol = 0;
        int totalCols = excelWSheet.getRow(rowNo).getPhysicalNumberOfCells();
        String[] arr = new String[totalCols];
        for (int i = startCol; i < totalCols; i++) {
            String cellData = getCellData(rowNo, i);
            arr[i] = cellData;
        }
        return arr;
    }

    public HashMap<String, String> excelDataToMap(int row) {
        HashMap<String, String> map = new HashMap<>();
        String[] header = getRowData(0);
        for (int i = 0; i < header.length; i++) {
            map.put(header[i], getCellData(row, i));
        }
        return map;
    }

    public synchronized HashMap<String, String> excelDataToMapContains(String colName, String value) {
        for (int i = 1; i <= countRows(); i++) {
            HashMap<String, String> map  = excelDataToMap(i);
            if (map.get(colName).equals(value)) {
                return excelDataToMap(i);
            }
        }
        Assert.fail("Can't find value: "+ value + " in column: " + colName);
        return null;
    }
}
