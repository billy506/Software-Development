package edu.virginia.cs.hw3;

import java.util.*;

public class JeffersonApportionmentStrategy extends ApportionmentStrategy{
    private List<State> stateList;
    private double divisor;
    private int targetRepresentatives;

    private Apportionment apportionment = new Apportionment();
    @Override
    public Apportionment getApportionment(List<State> stateList, int representatives) {
        initializeFields(stateList, representatives);
        divisor = getDivisor();
        return getIntegerApportionment();
    }

    private Apportionment getIntegerApportionment() {

        executeApportionment(adjustedDivisor(divisor));

        return apportionment;
    }

    private void executeApportionment(double divisor){
        for(State state : stateList) {
            apportionment.addRepresentativesToState(state,
                    (int) Math.floor(getQuota(state.getPopulation(),divisor)));
        }
    }

    private double getQuota(int population, double divisor){
        return population / divisor;
    }
    private double adjustedDivisor(double divisor)
    {
        int allocatedRepresentatives = 0;
        for(State state : stateList) {
            allocatedRepresentatives += Math.floor(getQuota(state.getPopulation(),divisor));
        }
        if(allocatedRepresentatives>targetRepresentatives){
            return adjustedDivisor(divisor * 1.05);
        }
        if(allocatedRepresentatives<targetRepresentatives){
            return adjustedDivisor(divisor * 0.95);
        }
        return divisor;
    }

    private void initializeFields(List<State> stateList, int representatives) {
        this.stateList = stateList;
        targetRepresentatives = representatives;
    }

    protected double getDivisor() {
        int totalPopulation = getTotalPopulation();
        return (double) totalPopulation / targetRepresentatives;
    }

    private int getTotalPopulation() {
        int totalPopulation = 0;
        for (State state : stateList) {
            totalPopulation += state.getPopulation();
        }
        return totalPopulation;
    }
}
