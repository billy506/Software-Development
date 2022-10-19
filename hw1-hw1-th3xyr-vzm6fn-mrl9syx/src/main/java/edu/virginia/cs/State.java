package edu.virginia.cs;

public class State implements Comparable<State> {
    String name;
    int n;
    double remainder;
    public State(String name, int n, double remainder){
        this.name = name;
        this.n = n;
        this.remainder = remainder;
    }

    public void setRemainder(double remainder){
        this.remainder = remainder;
    }

    public void setRep(int n){
        this.n = n;
    }
    public void add(){
        n++;
    }

    public String getName(){
        return name;
    }

    public int getRep(){
        return n;
    }

    public double getRemainder(){
        return remainder;
    }

    public int compareTo(State state1) {
        return this.name.compareTo(state1.getName());
    }
}
