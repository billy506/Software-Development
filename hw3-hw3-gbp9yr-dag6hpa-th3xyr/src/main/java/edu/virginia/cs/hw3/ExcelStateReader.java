package edu.virginia.cs.hw3;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ExcelStateReader extends StateReader{
    private String filename;
    private FileInputStream inputStream;
    private Workbook excelFile;
    private Iterator<Row> xlsxIterator;
    private final int STATE_NAME_CELL = 0;
    private final int STATE_POPULATION_CELL = 1;



    public ExcelStateReader(String filename) {
        if (!filename.toLowerCase().endsWith(".xlsx")) {
            throw new IllegalArgumentException("Error: cannot open non excel file " + filename);
        }
        this.filename = filename;
    }

    @Override
    public void readStates() {
        try {
            generateXLSXReader();
            readXLSXFile();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateXLSXReader() throws IOException {
        openFile();
        try{
            String sheetName = excelFile.getSheetName(0);
            xlsxIterator = excelFile.getSheet(sheetName).rowIterator();
            xlsxIterator.next();
        }catch (IndexOutOfBoundsException e) {}
    }

    public void openFile() throws IOException {
        //https://sde-coursepack.github.io/modules/construction/Example-Gradle-With-Poi/
        //Learned how to create an FileInputStream using a file name (and the workbook)
        inputStream = new FileInputStream(filename);
        excelFile = new XSSFWorkbook(inputStream);
    }

    public void readXLSXFile(){
        List<State> stateList = this.getStates();
        int rowNumber = 1;
        while (xlsxIterator.hasNext()) {
            rowNumber++;
            Row currentRow = xlsxIterator.next();
            State currentState = null;
            try {
                currentState = verifyStateInfo(currentRow, rowNumber);
            } catch (NumberFormatException e) {
                System.out.println("Row " + rowNumber + " contains an invalid state population. (Wrong Formatting or Blank)");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (currentState != null)
                stateList.add(currentState);
        }
    }

    public State verifyStateInfo(Row excelRow, int rowNumber){
        State newState = null;

        if(excelRow.getCell(0) == null)
            throw new IllegalArgumentException("Row "+rowNumber + " contains an invalid state name.");
        if(excelRow.getCell(1) == null)
            throw new IllegalArgumentException("Row "+ rowNumber + " contains an invalid state population.");

        //https://sde-coursepack.github.io/modules/construction/Example-Gradle-With-Poi/
        //Learned how to get data from the cells in a row
        String stateName = excelRow.getCell(STATE_NAME_CELL).getStringCellValue();
        Double statePopulation = excelRow.getCell(STATE_POPULATION_CELL).getNumericCellValue();
        if (!(stateName.isEmpty() && statePopulation == null)) {
            //Checks if a valid state name is contained
            if (stateName.isEmpty())
                throw new IllegalArgumentException("Row " + rowNumber + " contains an invalid state name.");
            //Checks if a valid state population is contained
            if (statePopulation < 0)
                throw new IllegalArgumentException("Row " + rowNumber + " contains an invalid state population. (Pop must be > 0)");
            if (statePopulation - Math.round(statePopulation) != 0)
                throw new IllegalArgumentException("Row " + rowNumber + " contains an invalid state population. (Pop must be an integer)");

            int statePop = (int) (double)statePopulation;
            //Initializes state to correct values
            newState = new State(stateName, statePop);
        }
        return newState;
    }
}
