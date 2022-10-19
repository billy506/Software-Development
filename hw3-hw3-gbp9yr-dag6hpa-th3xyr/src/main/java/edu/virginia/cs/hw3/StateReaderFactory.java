package edu.virginia.cs.hw3;

import java.io.IOException;

public class StateReaderFactory {

    StateReader getStateReader(String filename) throws IOException {
        if (filename.toLowerCase().endsWith(".csv"))
            return new CSVStateReader(filename);
        else if (filename.toLowerCase().endsWith(".xlsx"))
            return new ExcelStateReader(filename);
        else
            throw new IOException("The file has to end with .csv or .xlsx");
    }
}
