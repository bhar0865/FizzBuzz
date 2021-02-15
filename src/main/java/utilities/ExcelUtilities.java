package utilities;

import dto.Outcome;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods to read the test data
 */
public class ExcelUtilities {

    private static List<Outcome> outcome = new ArrayList<Outcome>();
    private static Outcome expectedOutcome = new Outcome();
    private static XSSFSheet sheet;
    private static Row row;
    private static CellType type;

    //This method is to read the test data and covert it into class object. The test data is converted to a format matching the json response.
    public static List<Outcome> readDataExcel(String dataFileName, String sheetName) {
        try {

            // Get Sheet from workbook
            sheet = getSheet(dataFileName,sheetName);
            if (sheet != null) {

                // For each row get the row cell value and add it to class object
                int rowCount = sheet.getPhysicalNumberOfRows();
                for (int i = 1; i < rowCount; i++) {
                    expectedOutcome = getRowCellValuesAndAddToClassObj(i);


                    /*For each column get the column cell value and add it to class object. If the same input has multiple outcomes, then add them as seperate input and output.
                    For example if input is 1 and output is Divided 1 by 3 and Divided 1 by 5, then add like below to class object
                    [1,Divided 1 by 3]
                    [1,Divided 1 by 5]
                    */
                    int columnNumber = row.getLastCellNum();

                    for (int j = 1; j <= columnNumber; j++) {
                        Cell currentCell = row.getCell(j);
                        expectedOutcome = getColumnCellValuesAndAddToClassObj(currentCell);

                    }
                    outcome.add(expectedOutcome);
                    expectedOutcome = new Outcome();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outcome;
    }

    //This method is used to get the row values from the test data sheet and add it to class object
    public static Outcome getRowCellValuesAndAddToClassObj(int i) {
        row = sheet.getRow(i);

        type = row.getCell(0).getCellType();
        if (type == CellType.NUMERIC) {
            expectedOutcome.setInput(String.valueOf(new Double(row.getCell(0).getNumericCellValue()).longValue()));
        }
        if (type == CellType.STRING) {
            expectedOutcome.setInput(row.getCell(0).getStringCellValue());
        }
        return expectedOutcome;
    }

    //This method is used to get the column values from the test data sheet and add it to class object
    public static Outcome getColumnCellValuesAndAddToClassObj(Cell currentCell) {
        if (currentCell != null) {
            type = currentCell.getCellType();
            String[] currentCellValue = currentCell.getStringCellValue().split("\n");
            if (type == CellType.STRING) {

                if (currentCellValue.length > 1) {
                    expectedOutcome.setResult(currentCellValue[0]);
                    outcome.add(expectedOutcome);
                    expectedOutcome = new Outcome();
                    expectedOutcome.setInput(String.valueOf(new Double(row.getCell(0).getNumericCellValue()).longValue()));
                    expectedOutcome.setResult(currentCellValue[1]);
                } else {
                    expectedOutcome.setResult(currentCellValue[0]);
                }

            }
            if (type == CellType.NUMERIC) {
                expectedOutcome.setResult(String.valueOf(new Double(currentCellValue[0]).longValue()));
            }
        }
        return expectedOutcome;
    }

    // This method is to get the sheet from the workbook.
    public static XSSFSheet getSheet(String dataFileName, String sheetName) throws IOException {

        FileInputStream fs = new FileInputStream(dataFileName);
        XSSFWorkbook workbook = new XSSFWorkbook(fs);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        return sheet;
    }
}