package edu.virginia.cs.hw3;

import java.util.*;

public class  HuntingtonHillApportionmentStrategy extends ApportionmentStrategy {
    private List<State> stateList;
    private int targetRepresentatives;
    private double divisor;
    private DecimalApportionment decimalApportionment;
    private Apportionment apportionment = new Apportionment();
    private double geometric_mean, quota;
    @Override
    public Apportionment getApportionment(List<State> stateList, int representatives) {
        initializeFields(stateList, representatives);
        divisor = getDivisor();
        return getIntegerApportionment();
    }

    private Apportionment getIntegerApportionment() {

        executeApportionment(adjusted_quota(getDivisor()));

        return apportionment;
    }

    private double getQuota(int population, double divisor){
        return population / divisor;
    }

    private double adjusted_quota(double divisor){
        int repCount = 0;
        for(State state : stateList){
            quota = getQuota(state.getPopulation(),divisor);
            geometric_mean = Math.sqrt(Math.floor(quota) * (Math.floor(quota)+1));
            repCount += Math.floor(quota);
            if (quota>geometric_mean)
            {
                repCount += 1;
            }
        }

        if(repCount > targetRepresentatives)
        {
            return adjusted_quota(divisor + 5);
        }

        if(repCount < targetRepresentatives)
        {
            return adjusted_quota(divisor - 5);
        }

        return divisor;
    }

    private void executeApportionment(double divisor) {
        for(State state : stateList) {
            quota = (state.getPopulation()) / divisor;
            geometric_mean = Math.sqrt(Math.floor(quota) * (Math.floor(quota)+1));
            if (quota>geometric_mean)
            {
                apportionment.addRepresentativesToState(state, (int)Math.ceil(quota));
            }
            else {
                apportionment.addRepresentativesToState(state, (int) Math.floor(quota));
            }
        }
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