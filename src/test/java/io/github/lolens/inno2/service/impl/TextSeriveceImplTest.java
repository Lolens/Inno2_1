package io.github.lolens.inno2.service.impl;

import io.github.lolens.inno2.entity.TextPart;
import io.github.lolens.inno2.service.TextService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TextSeriveceImplTest {

  private TextService service;

  @BeforeEach
  void setUp() {
    service = new TextServiceImpl();
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "It is a long established fact.",
      "It's a well-known established fact!",
      "It's 123 a_fact long-established things'",
      "Sentence? Sentence! Sentence."
  })
  void restoreShouldReconstructOriginalTextForVariousPunctuation(String original) {
    TextPart tree = service.parse(original);
    String restored = service.restore(tree);

    assertEquals(original, restored);
  }

  @Test
  void characterRestoreReturnsItContents() {
    TextPart character = service.parse("Hi.")
        .getChildren().getFirst()   // paragraph
        .getChildren().getFirst()   // sentence
        .getChildren().getFirst()   // lexeme
        .getChildren().getFirst()   // word
        .getChildren().getFirst();  // character "H"

    assertEquals("H", service.restore(character));
  }

  @Test
  void countCharactersShouldReturnCorrectValue() {
    TextPart text = service.parse("Lorem Ipsum is simply, dummy, text, of the printing and typesetting...");

    final int count = service.countChararacters(text);
    final int expectedCount = 60;

    assertEquals(expectedCount, count);
  }

  @Test
  void countLettersShouldReturnCorrectValue() {
    TextPart text = service.parse("Lorem Ipsum is simply, dummy, text, of the printing and typesetting...");

    final int count = service.countLetters(text);
    final int expectedCount = 54;

    assertEquals(expectedCount, count);
  }

  @Test
  void countLettersWithSpecificLetterShouldReturnCorrectValue() {
    TextPart text = service.parse("Lorem Ipsum is simply, dummy, text, of the printing and typesetting...");

    final int count = service.countLetters(text, 'm');

    assertEquals(5, count);
  }

  @Test
  void countLettersWithNonMatchingLetterShouldReturnZero() {
    TextPart text = service.parse("abc");

    assertEquals(0, service.countLetters(text, 'z'));
  }

  @Test
  void countSentencesContainingSameWordShouldReturnMaxSentenceCount() {
    String text = "I like it. It likes me. It is not a joke.";
    TextPart tree = service.parse(text);

    int result = service.countSentencesContainingSameWord(tree);

    assertEquals(3, result);
  }

  @Test
  void countSentencesContainingSameWordIsCaseInsensitive() {
    String text = "Cat is good. The cat purrs";
    TextPart tree = service.parse(text);

    int result = service.countSentencesContainingSameWord(tree);

    assertEquals(2, result);
  }

  @Test
  void countLettersShouldNotCountDigitsOrPunctuation() {
    TextPart text = service.parse("abc123!?.");

    assertEquals(3, service.countLetters(text));
  }

  @Test
  void countCharactersShouldCountDigitsAndPunctuationToo() {
    TextPart text = service.parse("ab!");

    assertEquals(3, service.countChararacters(text));
  }

  @Test
  void sortSentencesByCharCountShouldReorderSentencesInRestoredText() {
    String original = "Longest sentence here. Short. Medium one here.";
    TextPart tree = service.parse(original);

    service.sortSentencesByCharCount(tree);
    String restored = service.restore(tree);

    assertEquals("Short. Medium one here. Longest sentence here.", restored);
  }






}
