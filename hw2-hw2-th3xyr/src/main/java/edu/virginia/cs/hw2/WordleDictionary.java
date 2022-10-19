package edu.virginia.cs.hw2;

import java.util.*;
import java.io.*;

public class WordleDictionary {
    private static final int ALLOWED_WORD_SIZE = 5;
    private final Set<String> words;

    public WordleDictionary() {
        this(new HashSet<>());
    }

    protected WordleDictionary(Set<String> words) {
        this.words = words;
    }

    public boolean containsWord(String word) {
        return words.contains(word.toUpperCase());
    }

    public String getRandomWord() {
        if (0 == getDictionarySize()) {
            throw new EmptyDictionaryException("Cannot get random word from an empty dictionary");
        }
        return getRandomWordFromDictionarySet();
    }

    public int getDictionarySize() {
        return words.size();
    }

    private String getRandomWordFromDictionarySet() {
        int randomIndex = getRandomIndex();
        return getWordAtIndex(randomIndex);
    }

    private int getRandomIndex() {
        return (int) (Math.random() * getDictionarySize());
    }

    private String getWordAtIndex(int randomIndex) {
        Iterator<String> wordIterator = words.iterator();
        for (int i = 0; i < randomIndex - 1; i++) {
            wordIterator.next();
        }
        return wordIterator.next();
    }

    public boolean isLegalWordleWord(String word) {
        if (word == null) {return false;}
        if (word.length() == ALLOWED_WORD_SIZE) {
            word.toUpperCase();
            for (int i = 0; i < ALLOWED_WORD_SIZE; i++) {
                char ch = word.charAt(i);
                if (ch < 'A'|| ch > 'Z') {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void addWord(String word) {
        if (isLegalWordleWord(word)) {
            words.add(word.toUpperCase());
        }
//        else {
//            throw new IllegalWordException("Attempted to add: " + word + " to dictionary");
//        }
    }

    public void addWordsFromFileByFilename(String filename) {
        try (InputStream inputStream = new FileInputStream(filename)) {
            addWordsFromInputStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addWordsFromInputStream(InputStream inputStream) {
        try {
            BufferedReader bufferedReader = getBufferedReaderFromInputStream(inputStream);
            addWordsFromBufferedReader(bufferedReader);
            closeFileResources(inputStream, bufferedReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedReader getBufferedReaderFromInputStream(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        return new BufferedReader(inputStreamReader);
    }

    private void addWordsFromBufferedReader(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        while (line != null) {
            String candidateWord = line.strip();
            addWord(candidateWord.toUpperCase());
            line = bufferedReader.readLine();
        }
    }

    private void closeFileResources(InputStream inputStream, BufferedReader bufferedReader) throws IOException {
        inputStream.close();
        bufferedReader.close();
    }
}