package edu.virginia.cs.hw2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuessResultTest {

    private GuessResult testGuessResult;

    @BeforeEach
    public void setupTestResult() {
        testGuessResult = new GuessResult();
    }

    @Test
    @DisplayName("GuessResult constructor verification.")
    public void testThrowsErrorOnUninitializedFields() {
        assertNull(testGuessResult.getGuess());
        assertNull(testGuessResult.getAnswer());
        assertThrows(IllegalStateException.class, () ->
                testGuessResult.getGuessResult());
    }

    @Test
    @DisplayName("Correct answer Guess Result Test")
    public void testLetterResultCorrectAnswer() {
        givenInputGuessAndAnswer("MATCH", "MATCH");

        LetterResult[] guessResult = testGuessResult.getGuessResult();

        assertEquals("GGGGG", getLetterResultArrayAsString(guessResult));
    }

    private void givenInputGuessAndAnswer(String guess, String answer) {
        testGuessResult.setGuess(guess);
        testGuessResult.setAnswer(answer);
    }

    @Test
    @DisplayName("Helper function that converts the LetterResult[] in String for ease of use")
    public void testGetLetterResultArrayAsString() {
        LetterResult[] testArray = {LetterResult.GREEN, LetterResult.GRAY, LetterResult.YELLOW,LetterResult.GREEN, LetterResult.YELLOW};
        assertEquals("GgYGY", getLetterResultArrayAsString(testArray));
    }

    private String getLetterResultArrayAsString(LetterResult[] letterResultArray) {
        StringBuilder builder = new StringBuilder(5);
        for (LetterResult letterResult : letterResultArray) {
            char letterResultCharacter = getCharacterForLetterResult(letterResult);
            builder.append(letterResultCharacter);
        }
        return builder.toString();
    }

    @Test
    @DisplayName("LetterResult.Green -> `G`")
    public void testGetCharacterForLetterResult_Green() {
        assertEquals('G', getCharacterForLetterResult(LetterResult.GREEN));
    }

    @Test
    @DisplayName("LetterResult.Yellow -> `Y`")
    public void testGetCharacterForLetterResult_Yellow() {
        assertEquals('Y', getCharacterForLetterResult(LetterResult.YELLOW));
    }

    @Test
    @DisplayName("LetterResult.Gray -> `Y`")
    public void testGetCharacterForLetterResult_Gray() {
        assertEquals('g', getCharacterForLetterResult(LetterResult.GRAY));
    }

    private static char getCharacterForLetterResult(LetterResult letterResult) {
        return switch(letterResult) {
            case GRAY -> 'g';
            case GREEN -> 'G';
            case YELLOW -> 'Y';
        };
    }

    @Test
    public void testLetterResultIncorrectAnswer() {
        givenInputGuessAndAnswer("ABCDE", "ALPOI");

        LetterResult[] guessResult = testGuessResult.getGuessResult();

        assertEquals("Ggggg", getLetterResultArrayAsString(guessResult));
    }

    public void testSimilarAnswer() {
        givenInputGuessAndAnswer("PLATO", "PLOAT");

        LetterResult[] guessResult = testGuessResult.getGuessResult();

        assertEquals("GGYYY", getLetterResultArrayAsString(guessResult));
    }

}