package edu.virginia.cs.hw3;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Configuration config = loadConfigurationFromArguments(args);
        runApportioner(config);
    }

    private static void runApportioner(Configuration config) {
        Apportioner apportioner = new Apportioner(config);
        apportioner.run();
    }

    private static Configuration loadConfigurationFromArguments(String[] args) throws IOException {
        ArgumentsHandler argumentsHandler = new ArgumentsHandler(args);
        return argumentsHandler.getConfiguration();
    }
}