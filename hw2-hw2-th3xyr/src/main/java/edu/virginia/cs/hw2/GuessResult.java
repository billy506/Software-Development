package edu.virginia.cs.hw2;

import java.util.Arrays;

public class GuessResult {
    public static final int GUESS_RESULT_ARRAY_SIZE = 5;
    private final LetterResult[] guessResult =
            {LetterResult.GRAY, LetterResult.GRAY, LetterResult.GRAY, LetterResult.GRAY, LetterResult.GRAY};
    private String answer; //always uppercase
    private String guess; //always uppercase

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer.toUpperCase();
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess.toUpperCase();
    }

    public LetterResult[] getGuessResult() {
        verifyAllFieldsAreInitialized();
        if (guess.equals(answer)) {
            return getCorrectAnswerArray();
        }
        /////////////////// Bug Fixed By Billy Huang //////////////////
        for(int i=0;i<guess.length()-1;i++){
            for(int k=0;i<answer.length()-1;i++) {
                if(guess.charAt(i)==answer.charAt(i)){
                    guessResult[i] = LetterResult.GREEN;
                }
                else if (guess.charAt(i) == answer.charAt(k)) {
                    guessResult[i] = LetterResult.YELLOW;
                }
            }
        }
        ///////////////////////////////////////////////////////////////
        return guessResult;
        //TODO: Currently incomplete - implement via TDD - Write Tests in GuessResultsTest.java
    }

    private void verifyAllFieldsAreInitialized() {
        if (guess == null) {
            throw new IllegalStateException("The guess field in GuessResult must be initialized before calling getGuessResult");
        }
        if (answer == null) {
            throw new IllegalStateException("The guess field in GuessResult must be initialized before calling getGuessResult");
        }
    }

    private LetterResult[] getCorrectAnswerArray() {
        fillGuessResultArrayWithOneColor(LetterResult.GREEN);
        return guessResult;
    }

    private void fillGuessResultArrayWithOneColor(LetterResult letterResult) {
        Arrays.fill(guessResult, letterResult);
    }


}
