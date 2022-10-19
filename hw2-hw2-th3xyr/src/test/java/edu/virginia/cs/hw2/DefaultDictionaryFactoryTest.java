package edu.virginia.cs.hw2;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultDictionaryFactoryTest {
    DefaultDictionaryFactory testFactory;

    @BeforeEach
    public void setupTestFactory() {
        testFactory = new DefaultDictionaryFactory();
    }

    @Test
    public void testGetDefaultGuessesDictionary() {
        WordleDictionary defaultGuessesDictionary = testFactory.getDefaultGuessesDictionary();
        assertNotNull(defaultGuessesDictionary);
    }
    @Test
    public void testGetDefaultAnswersDictionary(){
        WordleDictionary defaultAnswersDictionary = testFactory.getDefaultAnswersDictionary();
        assertNotNull(defaultAnswersDictionary);
    }
}
