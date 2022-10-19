package edu.virginia.cs.hw3;

import java.util.*;
import java.io.*;

public class ArgumentsHandler {
    public static final int FILENAME_INDEX = 0;
    private final List<String> arguments;
    private Configuration config;

    public ArgumentsHandler(List<String> arguments) {
        if (arguments.size() < 1) {
            throw new IllegalArgumentException("Error: No arguments were included at runtime. Arguments expected\n" +
                    "statePopulationFilename [number of representatives] [--hamilton]");
        }
        this.arguments = arguments;
    }

    public ArgumentsHandler(String[] args) {
        this(Arrays.asList(args));
    }

    public Configuration getConfiguration() throws IOException {
        setDefaultConfiguration();
        configureStateReader();
        for(int i=1; i<arguments.size();i++){
            if(arguments.get(i).startsWith("-")) {
                if(arguments.get(i).startsWith("--")){
                    executeLongFlag(i+1,arguments.get(i).substring(2));
                }
                else{
                    for(int k=1;k<arguments.get(i).length();k++) {
                            executeShortFlag(i+k,arguments.get(i).charAt(k));
                        }
                    }
                }
            }
        return config;
    }
    private void executeLongFlag(int index, String arg) throws IOException{
        //Long Flag
        if(arg.equalsIgnoreCase("reps")) {
            checkForRepresentativeCount(index);
        }
        else if(arg.equalsIgnoreCase("format")) {
            setApportionmentFormatFromArgument(arguments.get(index));
        }
        else if(arg.equalsIgnoreCase("algorithm")) {
            setApportionmentStrategyFromArgument(arguments.get(index));
        }
        else{
            throw new IOException("Illegal Flag!");
        }
    }
    private void executeShortFlag(int index, char arg) throws IOException {
        //Short Flag
        if (arg == 'r') {
            checkForRepresentativeCount(index);
        }
        else if(arg == 'f') {
            setApportionmentFormatFromArgument(arguments.get(index));
        }
        else if(arg == 'a') {
            setApportionmentStrategyFromArgument(arguments.get(index));
        }
        else {
            throw new IOException("Illegal Flag!");
        }
    }

    private void setDefaultConfiguration() {
        config = new Configuration();
        config.setApportionmentStrategy(new HamiltonApportionmentStrategy());
        config.setRepresentatives(435);
        config.setApportionmentFormat(new AlphabeticalApportionmentFormat());
    }

    private void configureStateReader() throws IOException {
        String filename = arguments.get(FILENAME_INDEX);
        setStateReaderFromFilename(filename);
    }


    private void checkForRepresentativeCount(int index) {
        if (arguments.size() < 2) {
            return;
        }

        int representativeCount = Integer.parseInt(arguments.get(index));
        if (representativeCount <= 0) {
            throw new IllegalArgumentException("Error: Invalid representative count : " + representativeCount + " - number must be positive");
        }
        config.setRepresentatives(representativeCount);
    }

    private void setStateReaderFromFilename(String filename) throws IOException {
        if (!filenameExists(filename))
            throw new FileNotFoundException("The file doesn't exist!"+filename);

        StateReaderFactory factory = new StateReaderFactory();
        StateReader stateReader = factory.getStateReader(filename);
        config.setStateReader(stateReader);
    }

    private boolean filenameExists(String filename) {
        File file = new File(filename);
        return file.exists();
    }

    private void setApportionmentStrategyFromArgument(String algorithmName) throws IOException {
        ApportionmentStrategyFactory factory = new ApportionmentStrategyFactory();
        ApportionmentStrategy apportionmentStrategy = factory.getStrategy(algorithmName);
        config.setApportionmentStrategy(apportionmentStrategy);
    }

    private void setApportionmentFormatFromArgument(String formatName) throws IOException {
        ApportionmentFormatFactory factory = new ApportionmentFormatFactory();
        ApportionmentFormat apportionmentFormat = factory.getFormat(formatName);
        config.setApportionmentFormat(apportionmentFormat);
    }
}