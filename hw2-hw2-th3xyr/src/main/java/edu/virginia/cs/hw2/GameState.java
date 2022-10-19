package edu.virginia.cs.hw2;

public class GameState {
    public static final int MAX_LENGTH = 5;
    public static final int MAX_GUESSES = 6;
    private String answer;
    private int guessCount;
    private GameStatus gameStatus;
    private WordleDictionary legalGuessDictionary;

    private final LetterResult[] winningArray = {LetterResult.GREEN,LetterResult.GREEN,LetterResult.GREEN,LetterResult.GREEN,LetterResult.GREEN};

    protected GameState(String answer, WordleDictionary dictionary, int guessCount, GameStatus status) {
        this.answer = answer.toUpperCase();
        this.legalGuessDictionary = dictionary;
        this.guessCount = guessCount;
        this.gameStatus = status;
        if (0 == legalGuessDictionary.getDictionarySize()) {
            throw new EmptyDictionaryException("Error: Cannot play Wordle with an Empty Dictionary");
        }
        if (!legalGuessDictionary.containsWord(answer)) {
            throw new IllegalWordException(
                    "Created a game with an illegal answer not found in the dictionary: " + answer);
        }
    }

    public GameState(String answer, WordleDictionary dictionary) {
        this(answer, dictionary, 0, GameStatus.PLAYING);
    }

    public GameState(String answer) {
        this(answer, getDefaultGuessesDictionary(), 0, GameStatus.PLAYING);
    }

    private static WordleDictionary getDefaultGuessesDictionary() {
        DefaultDictionaryFactory factory = new DefaultDictionaryFactory();
        return factory.getDefaultGuessesDictionary();
    }

    public GameState() {
        this(getRandomAnswerFromDefaultDictionary(), getDefaultGuessesDictionary(), 1, GameStatus.PLAYING);
    }

    private static String getRandomAnswerFromDefaultDictionary() {
        DefaultDictionaryFactory factory = new DefaultDictionaryFactory();
        WordleDictionary answersDictionary = factory.getDefaultAnswersDictionary();
        return answersDictionary.getRandomWord();
    }

    public boolean isGameOver() {
        return GameStatus.PLAYING != gameStatus;
    }

    public boolean isWin() {
        return GameStatus.WON == gameStatus;
    }

    public boolean isLoss() {
        return GameStatus.LOST == gameStatus;
    }

    public int getGuessCount() {
        return guessCount;
    }

    public int getRemainingGuesses() {
        return MAX_GUESSES - guessCount;
    }

    public String getAnswer() {
        return answer;
    }

    public LetterResult[] submitGuess(String guess) {
        if(getRemainingGuesses()==0&&gameStatus==GameStatus.PLAYING){throw new GameAlreadyOverException("GAME OVER");}
        guessCount++;
        if(guess.length()!=MAX_LENGTH) {
            throw new IllegalWordException("This is Not a Five Letter Word!");
        }
        GuessResult guessResult = new GuessResult();
        guessResult.setGuess(guess);
        guessResult.setAnswer(answer);

        if(guessResult.getGuessResult() == winningArray)
            gameStatus = GameStatus.WON;

        if(getRemainingGuesses()==0&&gameStatus == GameStatus.LOST)
        {
            gameStatus = GameStatus.LOST;
        }

        return guessResult.getGuessResult();
    }

    public enum GameStatus {
        PLAYING, WON, LOST;
    }

}
