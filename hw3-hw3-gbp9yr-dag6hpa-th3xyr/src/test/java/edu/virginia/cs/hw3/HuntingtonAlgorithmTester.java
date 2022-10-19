package edu.virginia.cs.hw3;

import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HuntingtonAlgorithmTester {

    String filename;
    int representatives;
    HuntingtonHillApportionmentStrategy hill = new HuntingtonHillApportionmentStrategy();
    @Test
    @DisplayName("Hungtingon Hill tested with only one state")
    // Sample code from Piazza @281 by Prof. McBurney
    public void HuntingtonHill_OneState() {
        State Washington = new State("Washington", 1000); // I don't need real numbers here, dummy numbers are fine
        List<State> stateList = List.of(Washington); // Put virginia in a list - just a note that List.of was introduced in, I believe, JDK 15 or 16
        representatives = 25;
        Apportionment apportionment = hill.getApportionment(stateList, representatives);
        assertEquals(25, apportionment.getRepresentativesForState(Washington)); // Virginia should get all 10 reps
        assertEquals(1, apportionment.getStateSet().size()); //virginia should be the only state
    }
    @Test
    @DisplayName("Hungtingon Hill tested with shortened statelist")
    public void HuntingtonHill_ShortListofStates(){
        double[] expected = {11,1,8,5};
        filename = "src/test/java/files/test/xlsx/1990ShortenedCensus.xlsx";
        ExcelStateReader testReader = new ExcelStateReader(filename);
        testReader.readStates();
        List<State> input = testReader.getStates();
        representatives = 25;
        Apportionment apportionment = hill.getApportionment(input, representatives);
        for(int i = 0; i<input.size(); i++) {
            State currentState = input.get(i);
            System.out.println(apportionment.getRepresentativesForState(currentState));
            assertEquals(expected[i], apportionment.getRepresentativesForState(currentState), "Number of Representatives calculated incorrectly");
        }
    }
    @Test
    @DisplayName("Hungtingon Hill tested with sample state list")
    public void HuntingtonHill_SampleStatePop() {
        double[] expected = {1, 5, 10, 7, 2};
        filename = "src/test/java/files/test/xlsx/SampleStatePop.xlsx";
        ExcelStateReader testReader = new ExcelStateReader(filename);
        testReader.readStates();
        List<State> input = testReader.getStates();
        representatives = 25;
        Apportionment apportionment = hill.getApportionment(input, representatives);
        for (int i = 0; i < input.size(); i++) {
            State currentState = input.get(i);
            assertEquals(expected[i], apportionment.getRepresentativesForState(currentState), "Number of Representatives calculated incorrectly");
        }
    }
}
