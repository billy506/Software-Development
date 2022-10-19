package edu.virginia.cs.hw3;

import java.io.IOException;

public class ApportionmentFormatFactory {
    ApportionmentFormat getFormat(String formatName) throws IOException {
        if (formatName.toLowerCase().equals("alpha"))
            return new AlphabeticalApportionmentFormat();
        else if (formatName.toLowerCase().equals("benefit"))
            return new RelativeBenefitApportionmentFormat();
        else
            throw new IOException("Format name is invalid. Must be alpha or benefit. Default is alpha.");
    }
}
