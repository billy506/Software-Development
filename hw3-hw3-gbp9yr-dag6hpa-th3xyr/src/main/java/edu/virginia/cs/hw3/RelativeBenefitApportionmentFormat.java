package edu.virginia.cs.hw3;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class RelativeBenefitApportionmentFormat extends ApportionmentFormat{
    private Apportionment apportionment;
    private int totalPopulation;
    private double divisor;
    @Override
    public String getApportionmentString(Apportionment apportionment) {
        this.apportionment = apportionment;
        divisor = getTotalPopulation()/apportionment.getAllocatedRepresentatives();
        setBenefit();
        return getRelativeBenefitApportionmentString();
    }
    private String getRelativeBenefitApportionmentString(){
        return apportionment.getStateSet().stream()
                .sorted(Comparator.comparing(State::getBenefit).reversed())
                .map(this::getApportionmentStringForState)
                .collect(Collectors.joining("\n"));
    }

    private int getTotalPopulation() {
        return apportionment.getStateSet().stream()
                .mapToInt(State::getPopulation)
                .sum();
    }

    private String getApportionmentStringForState(State state){
        if(state.getBenefit()>0){
            return state.getName() + " - " + apportionment.getRepresentativesForState(state) + " - +" + state.getBenefit();
        }

        return state.getName() + " - " + apportionment.getRepresentativesForState(state) + " - " + state.getBenefit();

    }

    private void setBenefit(){
        for(State state:apportionment.getStateSet()){
            state.setBenefit(apportionment.getRepresentativesForState(state) - (state.getPopulation()/divisor));
        }
    }
}
