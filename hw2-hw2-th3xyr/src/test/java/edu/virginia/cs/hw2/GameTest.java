package edu.virginia.cs.hw2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private GameState testGame;
    private final LetterResult[] winningArray = {LetterResult.GREEN,LetterResult.GREEN,LetterResult.GREEN,LetterResult.GREEN,LetterResult.GREEN};

    @Test
    public void testConstructorWithIllegalAnswer() {
        assertThrows(IllegalWordException.class,
                () -> new GameState("QZXYX"));
    }

    public void testSubmitLongGuess(){
            assertThrows(IllegalWordException.class, ()->{testGame.submitGuess("ABCDEF");} );
    }

    public void testSubmitMultipleTime(){
        for(int i=1;i<5;i++){
            testGame.submitGuess("HELLO");
        }

        assertThrows(GameAlreadyOverException.class, ()->{testGame.submitGuess("HELLO");} );
    }

    public void testWonGame(){
        assertArrayEquals(winningArray, testGame.submitGuess(testGame.getAnswer()));
    }

    public void testEmptyGuess(){
        assertThrows(IllegalWordException.class, ()->{testGame.submitGuess("");} );
    }

    public void testLost(){
        for(int i=1;i<6;i++){
            testGame.submitGuess("HELLO");
        }
        assertTrue(testGame.isLoss());
    }
}