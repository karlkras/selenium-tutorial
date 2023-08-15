package com.letskodeit.overview;

import com.llts.test.utilities.Tools;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ExcelReaderExample {
    public static void main(String[] args) throws Exception {
        XSSFWorkbook excelWBook;
        XSSFSheet excelWSheet;
        XSSFCell excelCell;
        String path = Tools.getFilePath("testdata/ExampleData.xlsx");

        String sheetName = "Scenario1";

        FileInputStream excelFile = new FileInputStream(path);
        excelWBook = new XSSFWorkbook(excelFile);
        excelWSheet = excelWBook.getSheet(sheetName);
        excelCell = excelWSheet.getRow(1).getCell(1);
        String value = excelCell.getStringCellValue();
        System.out.printf("Cell data is:%s%n ", value);

    }
}
