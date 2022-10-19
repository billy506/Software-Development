package edu.virginia.cs.hw3;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExcelStateReaderTester {
    String filename = "src/test/java/files/test/xlsx/SampleStatePop.xlsx";
    ExcelStateReader testExcelReader = new ExcelStateReader(filename);

    private List<State> populate1990Census() {
        List<State> list = new ArrayList<State>();
        list.add(new State("New Jersey",7730188));
        list.add(new State("Rhode Island",1003464));
        list.add(new State("Massachusetts",6016425));
        list.add(new State("Connecticut",3287116));
        list.add(new State("Maryland",4780753));
        return list;
    }

/*    @Test
    public void testExcelStateReader(){
        List<State> expected = populate1990Census();
        StateReader InputReader =  ;
        List<State> actual = InputReader.getStates();

        for(int i = 0; i<expected.size(); i++) {
            assertEquals(expected.get(i).getName(), actual.get(i).getName(), "Read state name incorrectly");
            assertEquals(expected.get(i).getPopulation(), actual.get(i).getPopulation(), "Parsed population incorrectly");
        }
    }*/

    private List<State> SampleStatePop() {
        List<State> list = new ArrayList<State>();
        list.add(new State("Delaware",989948));
        list.add(new State("Maryland",6177224));
        list.add(new State("Pennsylvania",13002700));
        list.add(new State("Virginia",8631393));
        list.add(new State("West Virginia",1793716));
        return list;
    }
    @Test
    public void testReadExcel() {
        testExcelReader.readStates();
        List<State> actual = testExcelReader.getStates();
        List<State> expected = SampleStatePop();
        for(int i = 0; i<expected.size(); i++) {
            assertEquals(expected.get(i).getName(), actual.get(i).getName(), "Read state name incorrectly");
            assertEquals(expected.get(i).getPopulation(), actual.get(i).getPopulation(), "Parsed population incorrectly");
        }
    }
}
