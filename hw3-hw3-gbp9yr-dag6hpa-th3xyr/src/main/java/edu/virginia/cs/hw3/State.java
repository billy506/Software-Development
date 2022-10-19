package edu.virginia.cs.hw3;

public class State {
    private final String name;
    private final int population;

    private double benefit;


    public State(String name, int population) {
        this.name = name;
        this.population = population;
    }

    protected void setBenefit(double benefit){
        this.benefit = benefit;
    }

    protected double getBenefit(){ return benefit;}

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        if (getPopulation() != state.getPopulation()) return false;
        return getName().equals(state.getName());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getPopulation();
        return result;
    }
}
