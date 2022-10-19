package edu.virginia.cs.hw3;

import java.io.IOException;

public class ApportionmentStrategyFactory {

    ApportionmentStrategy getStrategy(String algorithmName) throws IOException{
        String lowerAlgorithmName = algorithmName.toLowerCase();
        if (lowerAlgorithmName.equals("hamilton"))
            return new HamiltonApportionmentStrategy();
        else if (lowerAlgorithmName.equals("jefferson"))
            return new JeffersonApportionmentStrategy();
        else if (lowerAlgorithmName.equals("huntington"))
            return new HuntingtonHillApportionmentStrategy();
        else
            throw new IOException("Invalid apportionment strategy. Must be one of hamilton, jefferson, and huntington.");
    }
}
