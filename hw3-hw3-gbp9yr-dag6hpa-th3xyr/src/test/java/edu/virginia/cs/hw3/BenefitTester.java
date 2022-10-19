package edu.virginia.cs.hw3;

import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BenefitTester {
    String filename;
    int representatives;
    HuntingtonHillApportionmentStrategy hill = new HuntingtonHillApportionmentStrategy();
    @Test
    @DisplayName("Benefit Related Format Test")
    public void Benefit_OneState() {
        State Washington = new State("Washington", 1000);
        List<State> stateList = List.of(Washington);
        representatives = 25;
        Apportionment apportionment = hill.getApportionment(stateList, representatives);
        RelativeBenefitApportionmentFormat benefit = new RelativeBenefitApportionmentFormat();
        System.out.println(benefit.getApportionmentString(apportionment));
    }
    @Test
    @DisplayName("Benefit Related Format Test with shortened statelist")
    public void Benefit_ShortListofStates(){
        filename = "src/test/java/files/test/xlsx/1990ShortenedCensus.xlsx";
        ExcelStateReader testReader = new ExcelStateReader(filename);
        testReader.readStates();
        List<State> input = testReader.getStates();
        RelativeBenefitApportionmentFormat benefit = new RelativeBenefitApportionmentFormat();
        representatives = 25;
        Apportionment apportionment = hill.getApportionment(input, representatives);
        System.out.println(benefit.getApportionmentString(apportionment));
    }
    @Test
    @DisplayName("Alphabetical Format Test with shortened statelist")
    public void Alphabetical_ShortListofStates() {
        filename = "src/test/java/files/test/xlsx/1990ShortenedCensus.xlsx";
        ExcelStateReader testReader = new ExcelStateReader(filename);
        testReader.readStates();
        List<State> input = testReader.getStates();
        AlphabeticalApportionmentFormat benefit = new AlphabeticalApportionmentFormat();
        representatives = 25;
        Apportionment apportionment = hill.getApportionment(input, representatives);
        System.out.println(benefit.getApportionmentString(apportionment));
    }
}
